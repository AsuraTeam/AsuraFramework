# asura

> asura是我们在实际使用过程中，抽取出来的公共组件，主要包括如下：    
  asura-base      对content增强处理，无法单独提供服务
  asura-commons   封装第三方的工具包提供util类
  asura-cache     缓存相关的功能封装    
  asura-dao       数据库访问读写分离相关    
  asura-log       统一日志处理相关    
  asura-rabbitmq  rabbitmq相关    
  asura-quartz    定时任务相关    
  


## What's New

### asura-0.0.9 
1、新增asura-cache sentinel支持


### asura-0.0.8
1、新增asura-commons   
2、规则base模块，提供基于jackson全新的json工具   
3、去除掉http3依赖   

### asura-0.0.6
1、zk模块升级到curator
2、基于curator的asura-conf
4、asura-pay支付和通用收款单相关内容
5、asura-base新增支持header的http请求    
6、 asura-base支持DES加密   

### asura-0.0.5    
1、asura-rabbitmq 支持客户端消费者断开连接后重连    
2、asura-rabbitmq 支持客户端消费者处理消费异常    
3、asura-log统一接管返回值为DataTransferObject（或者为DataTransferObject的String形式）异常处理    

### asura-0.0.4    
新增asura-pay模块     

### asura-0.0.3   
新增asura-log接管所有异常错误功能   

### asura-0.0.2   
asura-log对接CAT   
新增asura-rabbitmq   

### asura-0.0.1    
基础的组件功能    

## 版本发布文档
> 目前废弃掉使用package.bat文件deploy到远程仓库    

### 1、parent pom.xml文件
> parant pom.xml 需要设置好    
  1、部署的仓库地址    
  2、插件声明    
  3、仓库配置声明

    <!-- 设置好使用仓库地址 -->
    <properties>
      <repository.id>snapshots</repository.id>
      <repository.url>http://url/to/snapshots</repository.url>
    </properties>
    ...
    </plugins>
      ...
      <!-- install插件 -->
      <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
      </plugin>
  
      <!-- deploy插件 -->
      <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
      </plugin>
    </plugins>
    ...
    <distributionManagement>    
      <repository>
          <id>releases</id>
          <url>http://url/to/releases</url>
      </repository>
      <snapshotRepository>
          <id>snapshots</id>
          <url>http://url/to/snapshots</url>
      </snapshotRepository>
    <distributionManagement>    

### 2、child pom.xml

> 子模块pom.xml 注释默认的deploy goal改写deploy goal为deploy-file    
    
    <plugins>
      <!-- deploy插件 -->
      <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-deploy-plugin</artifactId>
          <executions>
              <execution>
                  <id>default-deploy</id>
                  <phase>none</phase>
              </execution>
              <execution>
                  <id>deploy-sms</id>
                  <phase>deploy</phase>
                  <goals>
                      <goal>deploy-file</goal>
                  </goals>
                  <configuration>
                      <file>target/com-asura-framework-sms-${version}.jar</file>
                      <repositoryId>${repository.id}</repositoryId>
                      <url>${repository.url}</url>
                      <groupId>com.asura</groupId>
                      <artifactId>com-asura-framework-sms</artifactId>
                      <version>${version}</version>
                      <packaging>jar</packaging>
                  </configuration>
              </execution>
          </executions>
      </plugin>
    </plugins>

### 3、部署到远程仓库

使用maven命令：

    mvn clean compile package deploy 
