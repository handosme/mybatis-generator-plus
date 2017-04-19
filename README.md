# MBP(Mybatis Generator Plus)

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/handosme/mybatis-generator-plus.svg?branch=master)](https://travis-ci.org/handosme/mybatis-generator-plus)

![mybatis-generator](http://ihansen.oss-cn-hangzhou.aliyuncs.com/jar/mbp/1.3.2-plus/MBP.jpg)

MBP(Mybatis Generator Plus)基于mybatis generator core v.1.3.2 源码扩展，增加一些特性,具体如下:
### 功能特性:  
* 修改org.mybatis.generator.api.dom.java.Interface类，新增private List<Field> fields字段，用于让Mapper接口支持静态常量成员;  
* 扩展Mybatis-Generator，自动生成支持Oracle、Mysql、Sqlserver分页查询和批量插入操作的自动代码，支持从Mapper接口读取数据源名称;
```java
@Test
public void selectPageTest() throws Exception {
	OperateLogExample relationshipsExample = new OperateLogExample();
    relationshipsExample.setPagination(0L,10L);
    List<OperateLog> operateLogList = operateLogMapper.selectByExample(relationshipsExample);
    //TODO verify
    System.out.println(operateLogList);
}
```
* 支持oracle使用SEQUENCE实现自增主键:  

> 需要事先建立好表主键对应的SEQUENCE,并且SEQUENCE的名称作出了要求:格式为table_name_SEQUENCE   

* Model类支持Builder模式创建,示例代码:
```java
User user = new User.Builder()
				.userName("insert_test")
				.creatTime(new Date())
				.updateTime(new Date())
				.build();
```  

### 使用方式一：添加jar到本地仓库:
本工具的使用方式和原生的MyBatis Generator使用方式一致,兼容原生版本,但是由于本工具还未提交到主流的mvn中央仓库,所以暂时提供在线下载的方式提供相关jar包:
#### v.1.3.2-plus  
v.1.3.2-plus jar file 下载地址:[mbp.jar](http://static-ali.ihansen.org/jar/mbp/1.3.2-plus/mbp.jar)       
v.1.3.2-plus sources file下载地址:[mbp-sources.jar](http://static-ali.ihansen.org/jar/mbp/1.3.2-plus/mbp-sources.jar)  
将jar安装到本地仓库的方式:
```
mvn install:install-file  -Dfile=/Users/user/download/mbp.jar  -DgroupId=org.ihansen.mbp  -DartifactId=mybatis-generator-plus -Dversion=1.3.2-plus -Dpackaging=jar
```
然后就可以使用maven方式引入MBP到自己的项目中了:
```xml
<dependency>
    <groupId>org.ihansen.mbp</groupId>
    <artifactId>mybatis-generator-plus</artifactId>
    <version>1.3.2-plus</version>
    <scope>test</scope>
</dependency>
```
生成文件的dmeo入口: 
[demo.MBPMain.main](https://github.com/handosme/mybatis-generator-plus/blob/master/src/test/java/demo/MBPMain.java)  


### 使用方式二：运行可执行jar文件:  
包含运行依赖包的可独立执行jar文件：[mbp-jar-with-dependencies.jar](http://static-ali.ihansen.org/jar/mbp/1.3.2-plus/mbp-jar-with-dependencies.jar)  
使用如下命令执行即可生成自动文件：
```bash
java -jar mbp-jar-with-dependencies.jar -configfile MybatisGeneratorCfg.xml -overwrite
```
供参考的MBP配置文件: 
[MybatisGeneratorCfg.xml](https://github.com/handosme/mybatis-generator-plus/blob/master/src/test/resources/MybatisGeneratorCfg.xml)  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <properties resource="dbcpconfig.properties"/>

    <context id="mbp_tables" targetRuntime="MyBatis3">
        <!-- 自定义序列化 -->
        <plugin type="org.ihansen.mbp.extend.CustomSerializablePlugin"></plugin>

        <!-- oracle/mysql/sqlserver方言插件 -->
        <plugin type="org.ihansen.mbp.extend.CustomPlugin">
            <property name="dbType" value="MYSQL"/>
        </plugin>

        <!-- 自定义Example类文件名字 -->
        <plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin">
            <property name="searchString" value="Example$"/>
            <property name="replaceString" value="EX"/>
        </plugin>

        <!-- 注释控制 -->
        <commentGenerator>
            <property name="suppressAllComments" value="true"/>
            <property name="suppressDate" value="true"/>
        </commentGenerator>

        <!-- 数据库连接 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://xxx.xxx.xxx.xxx:3306/dbname?characterEncoding=utf-8" 
                        userId="username"
                        password="password"/>


        <!-- 使用于Oracle自定义类型转换 -->
        <javaTypeResolver type="org.ihansen.mbp.extend.CustJavaTypeResolver">
            <!-- 属性可以控制是否强制DECIMAL和NUMERIC类型的字段转换为Java类型的java.math.BigDecimal,默认值为false -->
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- 数据表对应的实体层 -->
        <javaModelGenerator targetPackage="demo.domain" targetProject="src/test/java">
            <property name="enableSubPackages" value="true"/>
            <property name="enableSubPackages" value="true"/>
        </javaModelGenerator>

        <!-- sql mapper 隐射配置文件(xml配置文件) -->
        <sqlMapGenerator targetPackage="demo.mapperxml" targetProject="src/test/resources">
            <property name="enableSubPackages" value="true"/>
            <!-- 此属性表示是否和原xml合并,false表示覆盖 -->
            <property name="isMergeable" value="false"/>
        </sqlMapGenerator>

        <!-- 在ibatis2 中是dao层，但在mybatis3中，其实就是mapper接口 -->
        <javaClientGenerator targetPackage="demo.mapper" type="XMLMAPPER" targetProject="src/test/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 要对那些数据表进行生成操作，必须要有一个 -->
        <table tableName="test_user" domainObjectName="User">
            <!-- 自定义属性配置：设置此属性表示该字段自增 -->
            <property name="incrementField" value="id"/>
            <!-- 自定义属性配置：对应的数据源名称 -->
            <property name="dataSourceName" value="db6"/>
            <property name="enableCountByExample" value="true"/>
            <property name="enableUpdateByExample" value="true"/>
            <property name="enableDeleteByExample" value="true"/>
            <property name="enableSelectByExample" value="true"/>
            <property name="selectByExampleQueryId" value="true"/>
        </table>
    </context>
</generatorConfiguration>
```


### MBP的用户:
[![ihansen.org](http://ihansen.oss-cn-hangzhou.aliyuncs.com/img/ihansen.png)](http://www.ihansen.org/)
[![掌上110](http://ihansen.oss-cn-hangzhou.aliyuncs.com/img/110_6b54392.png)](http://www.lvwan.com/110.html)
[![某不知名公司](http://ihansen.oss-cn-hangzhou.aliyuncs.com/img/Notfamous.jpg)]()



