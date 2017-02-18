--if clientIP == nil then
--     clientIP = ngx.req.get_headers()["x_forwarded_for"]
--end

local memcached_server_config = "/etc/memcache.conf"
local memcached = require "resty.memcached"
local memc, err = memcached:new()
--memc:set_timeout(100)
local log = ngx.log
local match = string.match
local sub = string.sub
local len = string.len
local gsub = string.gsub
local byte = string.byte
local loadstring = loadstring

--lua_shared_dict server_md5 10m;
--lua_shared_dict server_data_info 10m;
--lua_shared_dict remove_server 1m;
--lua_shared_dict server_config 1m;

-- 虚拟数
local virtual_server_number = 200

--  使用ngx存储变量
local server_data_info = ngx.shared.server_data_info
local server_md5       = ngx.shared.server_md5
local remove_server    = ngx.shared.remove_server
local server_config   = ngx.shared.server_config

-- key 名字
local server_md5_key       = "server_md5"
local server_data_info_key = "server_data_info"

local function debugs(str,filepath)
   files = io.open(filepath,"a+")
   if type(str) == "table" then
      for key,value in pairs(str) 
      do
          files:write(key.."\n")
          files:write(value)
      end
   else
     files:write(str)
   end
   files:close()
end

local key_to_number = ngx.crc32_long

local function set_table_to_ngx_dict(ngx_key,ngx_dict,table)
    d = ""
    for k,v in pairs(table) 
    do
       if type(v) == "table" then
          d = d.."["..k.."] = {"
          for sk,sv in pairs(v) do
              d = d.."['"..sk.."']='"..sv.."',"
          end
          d = d.."},"
       else
          d = d.."["..k.."]='"..v.."',"
       end
    end
    local data = "return {" ..d.."}"
    if ngx_dict:get(ngx_key) then
       ngx_dict:replace(ngx_key,data)
    else
       ngx_dict:set(ngx_key,data)
    end
end

local function set_memcache_server( server, port ,action )
  server_info = server.." "..port
  if action == "adding" then
     remove_server:delete(server_info)
     ngx.log(ngx.ERR,"adding server "..server_info)
     return
  end
  ngx.log(ngx.ERR,"remove server "..server_info)
  remove_server:set(server_info,1)
end

local function get_server_address(server_info)
   local port = match(server_info," .*")
   local port = gsub(port," ","")

   local server = match(server_info,".* ")
   local server = gsub(server," ","")
   return server,port
end


local function get_server_data_info_from_config_file()
   s_number = {}
   s_data_info = {}
   for server_info in io.lines(memcached_server_config)
   do
      if string.match(server_info,"^[1-9]") then
        if not remove_server:get(server_info) then
            for a = 1,virtual_server_number do
               local kid_number =  key_to_number(server_info..a)
                      s_number[#s_number+1] = kid_number
                      local server,port = get_server_address(server_info)
                      s_data_info[kid_number] = {["port"] = port , ["server"] = server}
               end
        end
      end
   end
   table.sort(s_number)
   return s_number,s_data_info
end


local function reset_server_md5_file() 
       log(ngx.ERR,"reset_server_md5_file")
       local s_number,s_data_info = get_server_data_info_from_config_file()
       set_table_to_ngx_dict(server_md5_key,server_md5,s_number)
       server_data_info:set("r",1)
       set_table_to_ngx_dict(server_data_info_key,server_data_info,s_data_info)
end

local function check_server_active_remove(server,port)
  local count = 0
  for i=1,3 do 
    local ok, err = memc:connect(server, port)
    if not ok then count = count+1 end
  end
  if count == 3 then 
      reset_server_md5_file()
      set_memcache_server( server, port ) 
  end
end

local function check_server_active_adding(server,port)
    local ok, err = memc:connect(server, port)
    if not ok then return end
    set_memcache_server( server, port,"adding" )
    reset_server_md5_file()
end

local function check_server_active()
   local last_check_time = server_config:get("last_check")
   if not last_check_time then
           server_config:set("last_check",os.time())
           last_check_time = server_config:get("last_check")
   end
   if (os.time() - last_check_time) <= 10 then return end
   server_config:set("last_check",os.time())
   local server_config_string = loadstring(server_config:get("server_config"))()

   for key,server_info in pairs(server_config_string) 
   do
      local server,port = server_info["server"],server_info["port"]
      if  remove_server:get(server.." "..port) then
         check_server_active_adding(server,port)
      end
   end
end


local function get_args()
  local method = ngx.req.get_method()
  if method == "POST" then 
     args = ngx.req.get_post_args()
  else
     args = ngx.req.get_uri_args()
  end
  return args
end

local function get_post_args()
  local args = get_args()
  local action = args.action
  local key    = args.key
  local value  = args.value
  local expire = args.expire
  local flags = args.flags
  return action,key,value,expire,flags
end

local action,key,value,expire,flags = get_post_args()
local function set_key(server,port)
      if not value or not  expire then
            return "必要的参数{ action,key,value,expire,flags}"
      end
      
      local ok, err = memc:connect(server, port)
      if  err then 
          check_server_active_remove(server,port)
          return 
      end
      local result = memc:set(key,value,expire,flags)
      if result == "STORED" then
          ngx.print(1)
      else
          ngx.print(0)
      end
end

local function get_key(server,port)
       local action,key = get_post_args()
       local ok, err = memc:connect(server, port)
       if not ok then return nil end
       local res, flags,err = memc:get(key)
       if res == nil then
          return ""
       end
       return res
end


local function get_server_md5()
    local str = server_md5:get(server_md5_key)
    local arr = loadstring(str)()
    return arr
end

local function get_server_data_info()
    local str = server_data_info:get(server_data_info_key)
    local arr =  loadstring(str)()
    return arr
end

function binarySearch(key)
    local key_to_number = key_to_number(key)
    local array = get_server_md5()
    local floor = math.floor
    low = 1
    high = #array
    while(low <= high) do
        local mid = floor(( low + high) / 2)
        if array[mid]+0 < key_to_number+0   then
            low = mid + 1
        elseif array[mid]+0 > key_to_number+0 then
            high = mid - 1
        end
        if high - low <= 1 then
            local key = array[low]+0
            local ret = 1
            while(ret<100)do
               local server_info = get_server_data_info()[key]
               if server_info or ret >=100 then
                  return server_info["server"],server_info["port"]
               end
               ret = ret+1
            end
        end
    end
end
 

local function memcache_action()
  check_server_active()
  local server,port = binarySearch(key)
  if action == "set" then
        set_key(server,port)
  elseif action == "get" then
        local result = get_key(server,port)
        ngx.print(result)
  end
end

local function set_config_to_ngx_shared_dic()
   var = "return {"
   for server_info in io.lines(memcached_server_config)
   do
      if string.match(server_info,"^[1-9]") then
            local server,port = get_server_address(server_info)
            var = var .. "{['port']="..port..",['server'] ='"..server.."' },"
      end
   end
   var = var .. "}"
   ngx.log(ngx.ERR,"reset server_config")
   local key = "server_config"
   if server_config:get(key) then
       server_config:replace(key,var)
   else
       server_config:set("last_check",os.time())
       server_config:set(key,var)
   end
end

local function set_config_to_dict(key,cfg_md5)
   if server_md5:get(key) then
       server_md5:replace(key,cfg_md5)
   else
       server_md5:set(key,cfg_md5)
   end
   set_config_to_ngx_shared_dic()
end

local function check_config_file_modify()
    local key = "config_md5"
    local fh = io.open(memcached_server_config,"r")
    if fh then 
         str = fh:read(1000)
    end
    if not str then return end
    local cfg_md5 = ngx.md5(str)
    local ngx_dict_md5 = server_md5:get(key)
    if not ngx_dict_md5 then
            ngx.log(ngx.ERR,"set "..key)
            set_config_to_dict(key,cfg_md5)

    elseif ngx_dict_md5 ~= cfg_md5 then
            ngx.log(ngx.ERR,"reset "..key)
            set_config_to_dict(key,cfg_md5)
            reset_server_md5_file()
    end
end

if server_data_info:get("r") ~= 1 then
    log(ngx.ERR,"reset_server_md5 to ngx shared dict")
    set_config_to_ngx_shared_dic()
    reset_server_md5_file()
end 
check_config_file_modify()
memcache_action()
