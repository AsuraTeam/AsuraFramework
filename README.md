# asura

> asura是我们在实际使用过程中，抽取出来的公共组件，主要包括如下：    
  asura-base      对content增强处理，无法单独提供服务
  asura-commons   封装第三方的工具包提供util类
  asura-cache     缓存相关的功能封装    
  asura-dao       数据库访问读写分离相关    
  asura-log       统一日志处理相关    
  asura-rabbitmq  rabbitmq相关    
  asura-quartz    定时任务相关    
  
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
