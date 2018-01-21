package org.ihansen.mbp.extend;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.ihansen.mbp.extend.dbSupport.MysqlSupport;
import org.ihansen.mbp.extend.dbSupport.OracleSupport;
import org.ihansen.mbp.extend.dbSupport.SqlServerSupport;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.TableConfiguration;

/**
 * .支持oracle/mysql/sqlserver数据库分页查询<br/>
 * .支持oracle/mysql/sqlserver数据库插入时自增主键<br/>
 * .支持oracle/mysql/sqlserver数据库批量插入<br/>
 * 
 * @author 吴帅
 * @CreationDate 2015年8月2日
 * @version 1.0
 */
public class CustomPlugin extends PluginAdapter {
	private String dbType;
	private DBSupport dbSupport;

	/**
	 * 修改Model类
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addBuilder(topLevelClass);
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * Model类添加使用Builder设计模式的构造方式
	 * @author 吴帅
	 * @parameter @param topLevelClass
	 * @parameter @param introspectedTable
	 * @createDate 2016年1月15日 上午11:27:58
	 */
	private void addBuilder(TopLevelClass topLevelClass) {
		//1. add InnerClass(Static Nested Classes)
		InnerClass builder = new InnerClass("Builder");
		builder.setStatic(true);
		builder.setVisibility(JavaVisibility.PUBLIC);
		List<Field> fields = topLevelClass.getFields();
		for (Field field : fields) {
			if("serialVersionUID".equals(field.getName()))//Builder不需要序列化
				continue;
			builder.addField(field);
			Method setter = new Method(field.getName());
			setter.setVisibility(JavaVisibility.PUBLIC);
			setter.setReturnType(new FullyQualifiedJavaType("Builder"));
			setter.addParameter(new Parameter(field.getType(), field.getName()));
			setter.addBodyLine("this."+field.getName()+" = "+field.getName()+";");
			setter.addBodyLine("return this;");
			builder.addMethod(setter);
		}
		Method build = new Method("build");
		build.setVisibility(JavaVisibility.PUBLIC);
		build.setReturnType(new FullyQualifiedJavaType(topLevelClass.getType().getShortName()));
		build.addBodyLine("return new "+topLevelClass.getType().getShortName()+"(this);");
		builder.addMethod(build);
		topLevelClass.addInnerClass(builder);
		
		//2. add constructor
		Method constructor = new Method(topLevelClass.getType().getShortName());
		constructor.setConstructor(true);
		constructor.setVisibility(JavaVisibility.PRIVATE);
		constructor.addParameter(new Parameter(new FullyQualifiedJavaType("Builder"), "b"));
		for (Field field : fields) {
			if("serialVersionUID".equals(field.getName()))//Builder不需要序列化
				continue;
			constructor.addBodyLine(field.getName()+" = b."+field.getName()+";");
		}
		topLevelClass.addMethod(constructor);
		
		
		//3. add default constructor
		Method defConstructor = new Method(topLevelClass.getType().getShortName());
		defConstructor.setConstructor(true);
		defConstructor.setVisibility(JavaVisibility.PUBLIC);
		defConstructor.addBodyLine("super();");
		topLevelClass.addMethod(defConstructor);
	}

	/**
	 * 修改Example类
	 */
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) { 
		addPage(topLevelClass, introspectedTable);
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * 修改Mapper类
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// 1.增加批量插入方式签名
		addBatchInsertMethod(interfaze, introspectedTable);

		// 2.增加数据源名称常量
		addDataSourceNameField(interfaze, introspectedTable);

		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	/**
	 * 修改mapper.xml,支持分页和批量
	 */
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		dbSupport.sqlDialect(document, introspectedTable);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement newElement = dbSupport.adaptSelectByExample(element, introspectedTable);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(newElement, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement newElement = dbSupport.adaptSelectByExample(element, introspectedTable);
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(newElement, introspectedTable);
	}

	@Override
	public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		dbSupport.adaptInsertSelective(element, introspectedTable);
		return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
	}

	/**
	 * This plugin is always valid - no properties are required
	 */
	@Override
	public boolean validate(List<String> warnings) {

		dbType = properties.getProperty("dbType"); //$NON-NLS-1$

		boolean valid1 = stringHasValue(dbType);
		if (valid1) {
			dbType = dbType.toUpperCase();// 忽略大小写
			if (dbType.equals("ORACLE")) {
				dbSupport = new OracleSupport();
			}
			else if (dbType.equals("MYSQL")) {
				dbSupport = new MysqlSupport();
			}
			else if (dbType.equals("SQLSERVER")) {
				dbSupport = new SqlServerSupport();
			}
			else{// 不支持其他数据库
				valid1 = false;
				warnings.add(getString("RuntimeError.18", "RenameExampleClassPlugin", "searchString"));
			}
			Pattern.compile(dbType);
		}
		else {

			if (!stringHasValue(dbType)) {
				warnings.add(getString("ValidationError.18", "RenameExampleClassPlugin", "searchString")); //$NON-NLS-1$
			}
		}

		return (valid1);
	}

	/**
	 * 在Mapper类中增加批量插入方法声明
	 * 
	 * @author 吴帅
	 * @parameter @param interfaze
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月30日 下午4:43:32
	 */
	private void addBatchInsertMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		// 设置需要导入的类
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		Method ibsmethod = new Method();
		// 1.设置方法可见性
		ibsmethod.setVisibility(JavaVisibility.PUBLIC);
		// 2.设置返回值类型
		FullyQualifiedJavaType ibsreturnType = FullyQualifiedJavaType.getIntInstance();// int型
		ibsmethod.setReturnType(ibsreturnType);
		// 3.设置方法名
		ibsmethod.setName("insertBatch");
		// 4.设置参数列表
		FullyQualifiedJavaType paramType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType paramListType;
		if (introspectedTable.getRules().generateBaseRecordClass()) {
			paramListType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		}
		else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			paramListType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
		}
		else {
			throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$  
		}
		paramType.addTypeArgument(paramListType);

		ibsmethod.addParameter(new Parameter(paramType, "records"));

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(ibsmethod);
	}


	/**
	 * 修改Example类,添加分页支持
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		//.add offset Field
		Field offsetField = new Field();
		offsetField.setVisibility(JavaVisibility.PROTECTED);
		offsetField.setType(new FullyQualifiedJavaType("java.lang.Long"));
		offsetField.setName("offset");
		commentGenerator.addFieldComment(offsetField, introspectedTable);
		topLevelClass.addField(offsetField);

		//.add limit Field
		Field limitField = new Field();
		limitField.setVisibility(JavaVisibility.PROTECTED);
		limitField.setType(new FullyQualifiedJavaType("java.lang.Long"));
		limitField.setName("limit");
		commentGenerator.addFieldComment(limitField, introspectedTable);
		topLevelClass.addField(limitField);

		//.add end Field
		Field endField = new Field();
		endField.setVisibility(JavaVisibility.PROTECTED);
		endField.setType(new FullyQualifiedJavaType("java.lang.Long"));
		endField.setName("end");
		commentGenerator.addFieldComment(endField, introspectedTable);
		topLevelClass.addField(endField);

		//.add setPagination method
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("setPagination");
		method.addParameter(new Parameter(new FullyQualifiedJavaType("long"), "offset"));
		method.addParameter(new Parameter(new FullyQualifiedJavaType("long"), "limit"));
		method.addBodyLine("this.offset = offset;");
		method.addBodyLine("this.limit = limit;");
		method.addBodyLine("this.end = offset + limit;");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	/**
	 * 增加数据源名称字段
	 * 
	 * @author 吴帅
	 * @parameter @param interfaze
	 * @parameter @param introspectedTable
	 * @createDate 2015年10月2日 上午10:06:47
	 */
	private void addDataSourceNameField(Interface interfaze, IntrospectedTable introspectedTable) {
		TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
		Properties properties = tableConfiguration.getProperties();
		String dataSourceName = properties.getProperty("dataSourceName");
		if (dataSourceName == null || dataSourceName == "") {
			return;
		}
		Field field = new Field();
		field.setVisibility(JavaVisibility.PUBLIC);
		field.setStatic(true);
		field.setFinal(true);
		field.setType(FullyQualifiedJavaType.getStringInstance());
		field.setName("DATA_SOURCE_NAME");
		field.setInitializationString("\"" + dataSourceName + "\"");
		interfaze.addField(field);
	}
}
