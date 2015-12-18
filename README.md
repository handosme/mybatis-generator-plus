# mybatis-generator-core
基于mybatis-generator-core-1.3.2 源码的扩展，增加一些特性 <br>
程序运行入口：<a href="https://github.com/handosme/mybatis-generator-core/blob/master/src/main/java/org/ihansen/mybatis/generator/MainClass.java" target="_Blank">org.ihansen.mybatis.generator.MainClass.main(String[])</a><br>
自动代码配置文件：<a href="https://github.com/handosme/mybatis-generator-core/blob/master/src/main/resources/MybatisGeneratorCfg.xml" target="_Blank">/src/main/resources/MybatisGeneratorCfg.xml</a><br>

<h4>v1.0特性:</h4>
1.修改org.mybatis.generator.api.dom.java.Interface类，新增private List<Field> fields字段，用于让Mapper接口支持静态常量成员；<br>
2.扩展Mybatis-Generator，自动生成支持Oracle、Mysql、Sqlserver分页查询和批量插入操作的自动代码，支持从Mapper接口读取数据源名称.<br>



