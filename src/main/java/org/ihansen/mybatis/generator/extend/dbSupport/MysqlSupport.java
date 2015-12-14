package org.ihansen.mybatis.generator.extend.dbSupport;

import java.util.List;



import org.ihansen.mybatis.generator.extend.DBSupport;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MysqlSupport implements DBSupport
{
	/**
	 * 向&lt;mapper&gt;的子节点中添加内容支持批量和分页查询的sql代码块
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 上午10:20:11
	 */
	@Override
	public void sqlDialect(Document document, IntrospectedTable introspectedTable)
	{
		XmlElement parentElement = document.getRootElement();
		//1.产生mysql分页的limit条件 TODO limit分页方式待优化
		XmlElement paginationSuffixElement = new XmlElement("sql");
		paginationSuffixElement.addAttribute(new Attribute("id", "MysqlDialectSuffix"));
		XmlElement pageEnd = new XmlElement("if");
		pageEnd.addAttribute(new Attribute("test", "pageHelper != null"));
		pageEnd.addElement(new TextElement("<![CDATA[ limit #{pageHelper.begin}, #{pageHelper.length}]]>"));
		paginationSuffixElement.addElement(pageEnd);
		parentElement.addElement(paginationSuffixElement);
		
		// 2.增加批量插入的xml配置
		addBatchInsertXml(document, introspectedTable);
	}

	/**
	 * 增加批量插入的xml配置
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年8月9日 下午6:57:43
	 */
	@Override
	public void addBatchInsertXml(Document document, IntrospectedTable introspectedTable)
	{
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		StringBuilder dbcolumnsName = new StringBuilder();
		StringBuilder javaPropertyAndDbType = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : columns)
		{
			dbcolumnsName.append(introspectedColumn.getActualColumnName() + ",");
			if (!introspectedColumn.getActualColumnName().equals("ID"))// 不设置id
			{
				javaPropertyAndDbType.append("#{item." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "},");
			}
		}

		XmlElement insertBatchElement = new XmlElement("insert");
		insertBatchElement.addAttribute(new Attribute("id", "insertBatch"));
		insertBatchElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
		insertBatchElement.addElement(new TextElement("insert into " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));

		XmlElement trim1Element = new XmlElement("trim");
		trim1Element.addAttribute(new Attribute("prefix", "("));
		trim1Element.addAttribute(new Attribute("suffix", ")"));
		trim1Element.addAttribute(new Attribute("suffixOverrides", ","));
		trim1Element.addElement(new TextElement(dbcolumnsName.toString().replaceAll("ID,", "")));//mysql插入是主键自增,所以不需要设置主键值
		insertBatchElement.addElement(trim1Element);

		insertBatchElement.addElement(new TextElement("values"));

		XmlElement foreachElement = new XmlElement("foreach");
		foreachElement.addAttribute(new Attribute("collection", "list"));
		foreachElement.addAttribute(new Attribute("index", "index"));
		foreachElement.addAttribute(new Attribute("item", "item"));
		foreachElement.addAttribute(new Attribute("separator", ","));
		foreachElement.addElement(new TextElement("("));
		XmlElement trim2Element = new XmlElement("trim");
		trim2Element.addAttribute(new Attribute("suffixOverrides", ","));
		trim2Element.addElement(new TextElement(javaPropertyAndDbType.toString()));
		foreachElement.addElement(trim2Element);
		foreachElement.addElement(new TextElement(")"));
		insertBatchElement.addElement(foreachElement);

		document.getRootElement().addElement(insertBatchElement);
	}

	/**
	 * 在xml文件的查询配置中加入分页支持
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param preFixId
	 * @parameter @param sufFixId
	 * @createDate 2015年9月29日 上午11:59:06
	 */
	@Override
	public void adaptSelectByExample(XmlElement element)
	{
		XmlElement paginationElement = new XmlElement("include"); //$NON-NLS-1$   
		paginationElement.addAttribute(new Attribute("refid", "MysqlDialectSuffix"));
		element.getElements().add(paginationElement);
	}

	/**
	 * 在xml的插入配置增加查询序列配置
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 下午12:00:37
	 */
	@Override
	public void adaptInsertSelective(XmlElement element, IntrospectedTable introspectedTable)
	{
		
	}
}
