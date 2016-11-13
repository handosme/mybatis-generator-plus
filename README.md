# MBP(mybatis generator plus)
基于mybatis generator core v.1.3.2 源码的扩展，增加一些特性 
dmeo程序入口: [demo.MBPMain.main](href="https://github.com/handosme/mybatis-generator-core/blob/master/src/main/java/org/ihansen/mybatis/generator/MainClass.java)  
```java
public static final String CFG_FILE_PATH = MBPMain.class.getResource("/MybatisGeneratorCfg.xml").getFile();

	public static void main(String[] args) {
		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
		File configFile = new File(CFG_FILE_PATH);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		}
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(new ProgressCallback() {
				@Override
				public void startTask(String taskName) {
					System.out.println("startTask(String "+taskName+")");
				}
				@Override
				public void saveStarted(int totalTasks) {
					System.out.println("saveStarted(int "+totalTasks+")");
				}
				@Override
				public void introspectionStarted(int totalTasks) {
					System.out.println("introspectionStarted(int "+totalTasks+")");
				}
				@Override
				public void generationStarted(int totalTasks) {
					System.out.println("generationStarted(int "+totalTasks+")");
				}
				@Override
				public void done() {
					System.out.println("done()");
				}
				@Override
				public void checkCancel() throws InterruptedException {
					System.out.println("checkCancel()");
				}
			});
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
```


MBP配置文件参考: [MybatisGeneratorCfg.xml](https://github.com/handosme/mybatis-generator-core/blob/master/src/main/resources/MybatisGeneratorCfg.xml")  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <properties resource="dbcpconfig.properties"/>

    <context id="oracel_tables" targetRuntime="MyBatis3">
        <!-- 自定义序列化 -->
        <plugin type="org.ihansen.mbp.extend.CustomSerializablePlugin"></plugin>

        <!-- oracle/mysql/sqlserver方言插件 -->
        <plugin type="org.ihansen.mbp.extend.CustomPlugin">
            <property name="pageHelperClass" value="org.ihansen.mbp.extend.PageHelper"/>
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
        <jdbcConnection driverClass="${mysql.jdbc.driver}"
                        connectionURL="${db6.jdbc.url}" userId="${db6.jdbc.username}"
                        password="${db6.jdbc.password}"/>


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


#### v1.0特性:  
* 修改org.mybatis.generator.api.dom.java.Interface类，新增private List<Field> fields字段，用于让Mapper接口支持静态常量成员;  
* 扩展Mybatis-Generator，自动生成支持Oracle、Mysql、Sqlserver分页查询和批量插入操作的自动代码，支持从Mapper接口读取数据源名称;  
* Model类支持Builder模式创建;  




