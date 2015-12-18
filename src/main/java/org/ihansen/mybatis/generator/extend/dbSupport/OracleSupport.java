package org.ihansen.mybatis.generator.extend.dbSupport;

import java.util.List;
import java.util.Properties;

import org.ihansen.mybatis.generator.extend.DBSupport;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.TableConfiguration;

public class OracleSupport implements DBSupport {
	/**
	 * 向&lt;mapper&gt;中子节点中添加支持批量和分页查询的sql代码块
	 * 
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 上午10:20:11
	 */
	@Override
	public void sqlDialect(Document document, IntrospectedTable introspectedTable) {
		XmlElement parentElement = document.getRootElement();
		// 1.产生分页语句前半部分
		XmlElement paginationPrefixElement = new XmlElement("sql");
		paginationPrefixElement.addAttribute(new Attribute("id", "OracleDialectPrefix"));
		XmlElement pageStart = new XmlElement("if");
		pageStart.addAttribute(new Attribute("test", "pageHelper != null"));
		pageStart.addElement(new TextElement("select * from ( select row_.*, rownum rownum_ from ( "));
		paginationPrefixElement.addElement(pageStart);
		parentElement.addElement(paginationPrefixElement);

		// 2.产生分页语句后半部分
		XmlElement paginationSuffixElement = new XmlElement("sql");
		paginationSuffixElement.addAttribute(new Attribute("id", "OracleDialectSuffix"));
		XmlElement pageEnd = new XmlElement("if");
		pageEnd.addAttribute(new Attribute("test", "pageHelper != null"));
		pageEnd.addElement(new TextElement("<![CDATA[ ) row_  where rownum <= #{pageHelper.end} ) where rownum_ > #{pageHelper.begin}  ]]>"));
		paginationSuffixElement.addElement(pageEnd);
		parentElement.addElement(paginationSuffixElement);

		// 3.获取序列的xml配置
		XmlElement tableSequence = new XmlElement("sql");
		tableSequence.addAttribute(new Attribute("id", "TABLE_SEQUENCE"));
		String tableSequenceNextval = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "_SEQUENCE.nextval";
		tableSequence.addElement(new TextElement(tableSequenceNextval));
		parentElement.addElement(tableSequence);

		// 4.增加批量插入的xml配置
		addBatchInsertXml(document, introspectedTable);
	}

	/**
	 * 生成批量插入的动态sql代码
	 * 
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年8月9日 下午6:57:43
	 */
	@Override
	public void addBatchInsertXml(Document document, IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		//获得要自增的列名
		String incrementField = introspectedTable.getTableConfiguration().getProperties().getProperty("incrementField");
		if(incrementField!=null){
			incrementField = incrementField.toUpperCase();
		}
		
		StringBuilder dbcolumnsName = new StringBuilder();
		StringBuilder javaPropertyAndDbType = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : columns) {
			String columnName = introspectedColumn.getActualColumnName();
			dbcolumnsName.append(columnName + ",");
			if (!columnName.toUpperCase().equals(incrementField)){// 不设置id
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
		trim1Element.addElement(new TextElement(dbcolumnsName.toString()));
		insertBatchElement.addElement(trim1Element);

		insertBatchElement.addElement(new TextElement("select " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "_SEQUENCE.nextval,A.* from("));

		XmlElement foreachElement = new XmlElement("foreach");
		foreachElement.addAttribute(new Attribute("collection", "list"));
		foreachElement.addAttribute(new Attribute("index", "index"));
		foreachElement.addAttribute(new Attribute("item", "item"));
		foreachElement.addAttribute(new Attribute("separator", "UNION ALL"));
		foreachElement.addElement(new TextElement("SELECT"));
		XmlElement trim2Element = new XmlElement("trim");
		trim2Element.addAttribute(new Attribute("suffixOverrides", ","));
		trim2Element.addElement(new TextElement(javaPropertyAndDbType.toString()));
		foreachElement.addElement(trim2Element);
		foreachElement.addElement(new TextElement("from dual"));
		insertBatchElement.addElement(foreachElement);

		insertBatchElement.addElement(new TextElement(") A"));

		document.getRootElement().addElement(insertBatchElement);
	}

	/**
	 * 向查询节点中添加分页支持
	 * 
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param preFixId
	 * @parameter @param sufFixId
	 * @createDate 2015年9月29日 上午11:59:06
	 */
	@Override
	public XmlElement adaptSelectByExample(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement pageStart = new XmlElement("include"); //$NON-NLS-1$   
		pageStart.addAttribute(new Attribute("refid", "OracleDialectPrefix"));
		element.getElements().add(0, pageStart);
		XmlElement isNotNullElement = new XmlElement("include"); //$NON-NLS-1$   
		isNotNullElement.addAttribute(new Attribute("refid", "OracleDialectSuffix"));
		element.getElements().add(isNotNullElement);
		return element;
	}

	/**
	 * 在单条插入动态sql中增加查询序列，以实现oracle主键自增
	 * 
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 下午12:00:37
	 */
	@Override
	public void adaptInsertSelective(XmlElement element, IntrospectedTable introspectedTable) {
		TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
		Properties properties = tableConfiguration.getProperties();
		String incrementFieldName = properties.getProperty("incrementField");
		if (incrementFieldName != null) {// 有自增字段的配置
			List<Element> elements = element.getElements();
			XmlElement selectKey = new XmlElement("selectKey");
			selectKey.addAttribute(new Attribute("keyProperty", incrementFieldName));
			selectKey.addAttribute(new Attribute("resultType", "java.lang.Long"));
			selectKey.addAttribute(new Attribute("order", "BEFORE"));
			selectKey.addElement(new TextElement("select "));
			XmlElement includeSeq = new XmlElement("include");
			includeSeq.addAttribute(new Attribute("refid", "TABLE_SEQUENCE"));
			selectKey.addElement(includeSeq);
			selectKey.addElement(new TextElement(" from dual"));
			elements.add(0, selectKey);
		}
	}
}
