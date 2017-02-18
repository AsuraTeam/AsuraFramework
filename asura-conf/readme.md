### 启动说明  
#### 1、添加spring配置文件  
在工程 classpath 目录下的 META-INF/spring 下添加 application-config-subscriber.xml 文件,内容如下  

    <bean class="com.asura.framework.conf.subscribe.InitSubscriber" init-method="init"/>  
  
#### 2、配置文件启动方式  
在工程classpath目录下添加 config.properties  
> 应用系统名称  
  config.application.name=asura-conf  
  #ZK地址及端口  
  zookeeper.config.ensemble=zk.soa.d.com:2181  
  #ZKsession超时时间  
  zookeeper.session.timeout=15000  
  #ZK连接超时时间  
  zookeeper.connection.timeout=5000  
  # 要加载的配置文件名，多个用逗号隔开  
  properties.resources.name=jdbc,move,clean  
  # 默认要扫描的jar文件前缀，多个用逗号隔开  
  auto.scan.jar.file.prefix=sms-biz,hermes-foundation

#### 3、JVM启动添加 -D 参数    
添加以下JVM参数  
> -Dconfig.application.name=asura-conf  
  -Dzookeeper.config.ensemble=zk.soa.d.com:2181  
  -Dzookeeper.session.timeout=15000  
  -Dzookeeper.connection.timeout=5000  
  -Dproperties.resources.name=jdbc,move,clean  
  -Dauto.scan.jar.file.prefix=sms-biz,hermes-foundation  

#### 4、读取配置优先级
1、JVM配置  
2、zookeeper  
3、配置文件 

#### 5、@AsuraSubField注解使用 
1、使用@AsuraSubField注解的类必须交由spring托管  
2、@AsuraSubField注解需要标注在get方法上  
3、注解属性描述：  
  appName：配置所属的系统  
  type：配置类型  
  code：配置编码  
  defaultValue：在系统配置中找不到相关配置时，所使用的默认值

      @Component("scheduleProvider.moveTransactionInitConstant")
      public class MoveTransactionInitConstant {
      
          public static final String FROM_CURRENT_DATE = "0";
      
          /**
           * 是否从当前日期开始初始化 0：否 1：是
           */
          private String fromCurrentDate;
      
          /**
           * 初始化几天数据
           */
          private String dateStep;
      
          /**
           * 初始化几天数据初始化几天数据
           */
          private String fromAfterDays;
      
          @AsuraSubField(appName = "hermes-schedule", type = "move-transaction-init", code = "from-current-date", defaultValue = "0")
          public String getFromCurrentDate() {
              return fromCurrentDate;
          }
      
          public void setFromCurrentDate(String fromCurrentDate) {
              this.fromCurrentDate = fromCurrentDate;
          }
      
          @AsuraSubField(appName = "hermes-schedule", type = "move-transaction-init", code = "date-step", defaultValue = "1")
          public String getDateStep() {
              return dateStep;
          }
      
          public void setDateStep(String dateStep) {
              this.dateStep = dateStep;
          }
      
          @AsuraSubField(appName = "hermes-schedule", type = "move-transaction-init", code = "from-after-days", defaultValue = "8")
          public String getFromAfterDays() {
              return fromAfterDays;
          }
      
          public void setFromAfterDays(String fromAfterDays) {
              this.fromAfterDays = fromAfterDays;
          }
      }  
  
#### 6、@AsuraSub注解使用  
1、@AsuraSub注解使用在枚举类上  
2、枚举类需要包含以下四个属性  
   String appName：配置所属的系统    
   String type：配置类型    
   String code：配置编码    
   Object defaultValue：在系统配置中找不到相关配置时，所使用的默认值  

    @AsuraSub
    public enum TestNewEnum {
    
        INFO_BY_TOKEN_URL("asura-conf", "hermes-foundation", "info-by-token-url", "http://passport.api.qa.com/users/info/v1");
    
        private String appName;
        private String type;
        private String code;
        private Object defaultValue;
    
        TestNewEnum(String appName, String type, String code, Object defaultValue) {
            this.appName = appName;
            this.type = type;
            this.code = code;
            this.defaultValue = defaultValue;
        }
    
        public String getAppName() {
            return appName;
        }
    
        public String getType() {
            return type;
        }
    
        public String getCode() {
            return code;
        }
    
        public Object getDefaultValue() {
            return defaultValue;
        }
    
    }