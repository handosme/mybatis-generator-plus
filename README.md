# MBP(Mybatis Generator Plus)

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/handosme/mybatis-generator-plus.svg?branch=master)](https://travis-ci.org/handosme/mybatis-generator-plus)
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg)](http://search.maven.org/#artifactdetails%7Corg.ihansen.mbp%7Cmybatis-generator-plus%7C1.1%7Cjar)

![mybatis-generator](http://ihansen.oss-cn-hangzhou.aliyuncs.com/jar/mbp/1.3.2-plus/MBP.jpg)

### 1.介绍:  
MBP(Mybatis-generator Plus)基于mybatis-generator-core v.1.3.2源码扩展，增加如下主要特性:
1. 扩展Mybatis-Generator，自动生成支持Oracle、Mysql、Sqlserver分页查询和批量插入操作的自动代码，支持从Mapper接口读取数据源名称;
```java
//分页查询demo
@Test
public void selectPageTest() throws Exception {
	OperateLogExample relationshipsExample = new OperateLogExample();
    relationshipsExample.setPagination(0L,10L);
    List<OperateLog> operateLogList = operateLogMapper.selectByExample(relationshipsExample);
    //...
```
2. 支持Mapper接口设置数据源，可用于业务垂直分库;
```java
public interface OperateLogMapper {
    public static final String DATA_SOURCE_NAME = "logDB";//这里可以用于标示数据源名称
    //...
```
3. 支持oracle使用SEQUENCE实现自增主键:  
*需要建立表主键对应的SEQUENCE,并且SEQUENCE的名称作出了要求:格式为table_name_SEQUENCE*
4. Model类支持Builder模式创建,示例代码:
```java
User user = new User.Builder()
				.userName("insert_test")
				.creatTime(new Date())
				.updateTime(new Date())
				.build();
```  

### 2.使用方式
#### 2.1. 方式一：运行可执行jar文件[推荐] 
包含运行依赖包的可独立执行jar文件：[mbp-jar-with-dependencies.jar](http://static-ali.ihansen.org/jar/mbp/1.3.2-plus/mbp-jar-with-dependencies.jar)   
供参考的MBP配置文件: 
[MybatisGeneratorCfg.xml](https://github.com/handosme/mybatis-generator-plus/blob/master/src/test/resources/MybatisGeneratorCfg.xml)  
使用如下命令执行即可生成自动文件：
```bash
java -jar mbp-jar-with-dependencies.jar -configfile MybatisGeneratorCfg.xml -overwrite
```


#### 2.2. 方式二：main方法运行
本工具的使用方式和原生的MyBatis Generator使用方式一致，兼容原生版本。maven 坐标:
```xml
<dependency>
  <groupId>org.ihansen.mbp</groupId>
  <artifactId>mybatis-generator-plus</artifactId>
  <version>1.1</version>
    <exclusions>
       <exclusion>
           <groupId>org.mybatis.generator</groupId>
           <artifactId>mybatis-generator</artifactId>
	   </exclusion>
    </exclusions>
  <scope>test</scope>
</dependency>
```
你也可以选择直接下载jar文件，然后安装到本地maven仓库:   
v.1.1 jar file 下载地址:[mbp.jar](http://static-ali.ihansen.org/jar/mbp/1.3.2-plus/mbp.jar)       
v.1.1 sources file下载地址:[mbp-sources.jar](http://static-ali.ihansen.org/jar/mbp/1.3.2-plus/mbp-sources.jar)  
将jar安装到本地仓库的方式:
```
mvn install:install-file  -Dfile=/Users/user/download/mbp.jar  -DgroupId=org.ihansen.mbp  -DartifactId=mybatis-generator-plus -Dversion=1.1 -Dpackaging=jar
```

生成文件的dmeo入口: 
[demo.MBPMain.main](https://github.com/handosme/mybatis-generator-plus/blob/master/src/test/java/demo/MBPMain.java)  





### 3.MBP的用户:
[![ihansen.org](http://ihansen.oss-cn-hangzhou.aliyuncs.com/img/ihansen.png)](http://www.ihansen.org/)
[![掌上110](http://ihansen.oss-cn-hangzhou.aliyuncs.com/img/110_6b54392.png)](http://www.lvwan.com/110.html)
[![某不知名公司](http://ihansen.oss-cn-hangzhou.aliyuncs.com/img/Notfamous.jpg)]()



