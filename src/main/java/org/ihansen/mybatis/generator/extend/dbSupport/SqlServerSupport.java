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

public class SqlServerSupport implements DBSupport {
	/**
	 * 向&lt;mapper&gt;的子节点中添加内容支持批量和分页查询的sql代码块
	 * 
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 上午10:20:11
	 */
	@Override
	public void sqlDialect(Document document, IntrospectedTable introspectedTable) {
		XmlElement parentElement = document.getRootElement();
		// 1.1 产生sqlserver分页的前缀语句块
		XmlElement paginationPrefixElement = new XmlElement("sql");
		paginationPrefixElement.addAttribute(new Attribute("id", "SqlServerDialectPrefix"));
		XmlElement prefixIf = new XmlElement("if");
		prefixIf.addAttribute(new Attribute("test", "pageHelper != null"));
		prefixIf.addElement(new TextElement("SELECT *FROM ("));
		paginationPrefixElement.addElement(prefixIf);
		parentElement.addElement(paginationPrefixElement);
		
		// 1.2 产生sqlserver分页的后缀语句块
		XmlElement paginationSuffixElement = new XmlElement("sql");
		paginationSuffixElement.addAttribute(new Attribute("id", "SqlServerDialectSuffix"));
		XmlElement suffixIf = new XmlElement("if");
		suffixIf.addAttribute(new Attribute("test", "pageHelper != null"));
		suffixIf.addElement(new TextElement("<![CDATA[) _pagination_tab WHERE _pagination_rownumber >=#{pageHelper.begin} and _pagination_rownumber < #{pageHelper.end}]]>"));
		paginationSuffixElement.addElement(suffixIf);
		parentElement.addElement(paginationSuffixElement);

		// 2.增加批量插入的xml配置
		addBatchInsertXml(document, introspectedTable);
	}

	/**
	 * 增加批量插入的xml配置
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
			if(!columnName.toUpperCase().equals(incrementField)){//不是自增字段的才会出现在批量插入中
				dbcolumnsName.append(columnName + ",");
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
	 * 
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param preFixId
	 * @parameter @param sufFixId
	 * @createDate 2015年9月29日 上午11:59:06
	 */
	@Override
	public XmlElement adaptSelectByExample(XmlElement element, IntrospectedTable introspectedTable) {
		//1.先取出原元素
		List<Element> elements = element.getElements();
		Element element0 = elements.get(0);//select
		XmlElement element1 = (XmlElement) elements.get(1);//<if test="distinct">distinct</if>
		XmlElement element2 = (XmlElement) elements.get(2);//<include refid="Base_Column_List" />
		Element element3 = elements.get(3);//from bus_log
		XmlElement element4 = (XmlElement) elements.get(4);//<if test="_parameter != null"><include refid="Example_Where_Clause" /></if>
		XmlElement element5 = (XmlElement)elements.get(5);//<if test="orderByClause != null">order by ${orderByClause}</if>
		
		element.removeElement(0);
		element.removeElement(0);
		element.removeElement(0);
		element.removeElement(0);
		element.removeElement(0);
		element.removeElement(0);
//		element.removeElement(1);
//		element.removeElement(2);
//		element.removeElement(3);
//		element.removeElement(4);
//		element.removeElement(5);
		
		
		//part1 <include refid="SqlServerDialectPrefix" />
		XmlElement prefix = new XmlElement("include");
		prefix.addAttribute(new Attribute("refid", "SqlServerDialectPrefix"));
		element.addElement(prefix);
		//part2 select
		element.addElement(element0);
		//part3 <if test="distinct"> distinct	</if>
		element.addElement(element1);
		//part4 row_number() over(
		element.addElement(new TextElement("row_number() over("));
		//part5 <if test="orderByClause != null"> order by ${orderByClause}	</if>
		element.addElement(element5);
		//part6 <if test="orderByClause == null"> order by incrementField </if>
		String incrementField = introspectedTable.getTableConfiguration().getProperties().getProperty("incrementField");
		XmlElement incrementFieldIf = new XmlElement("if");
		incrementFieldIf.addAttribute(new Attribute("test", "orderByClause == null"));
		incrementFieldIf.addElement(new TextElement("order by "+incrementField));
		element.addElement(incrementFieldIf);
		//part7 ) as _pagination_rownumber,
		element.addElement(new TextElement(") as _pagination_rownumber,"));
		//part8 <include refid="Base_Column_List" />
		element.addElement(element2);
		//part9 from table_name
		element.addElement(element3);
		//part10 <if test="_parameter != null"><include refid="Example_Where_Clause" /></if>
		element.addElement(element4);
		//part11 <include refid="SqlServerDialectSuffix" />
		XmlElement suffix = new XmlElement("include");
		suffix.addAttribute(new Attribute("refid", "SqlServerDialectSuffix"));
		element.addElement(suffix);
		
		//2.创建新元素
//		XmlElement sqlserverSelectByExampleElement = new XmlElement(element.getName());
//		List<Attribute> attributes = element.getAttributes();
//		for (Attribute attribute : attributes) {
//			sqlserverSelectByExampleElement.addAttribute(attribute);
//		}
		
		//3.构造新元素
		//part1 <include refid="SqlServerDialectPrefix" />
//		XmlElement prefix = new XmlElement("include");
//		prefix.addAttribute(new Attribute("refid", "SqlServerDialectPrefix"));
//		sqlserverSelectByExampleElement.addElement(prefix);
		//part2 select
//		sqlserverSelectByExampleElement.addElement(elements.get(0));
		//part3 <if test="distinct"> distinct	</if>
//		sqlserverSelectByExampleElement.addElement(elements.get(1));
		//part4 row_number() over(
//		sqlserverSelectByExampleElement.addElement(new TextElement("row_number() over("));
		//part5 <if test="orderByClause != null"> order by ${orderByClause}	</if>
//		sqlserverSelectByExampleElement.addElement(elements.get(5));
		//part6 <if test="orderByClause == null"> order by incrementField </if>
//		String incrementField = introspectedTable.getTableConfiguration().getProperties().getProperty("incrementField");
//		XmlElement incrementFieldIf = new XmlElement("if");
//		incrementFieldIf.addAttribute(new Attribute("test", "orderByClause == null"));
//		incrementFieldIf.addElement(new TextElement("order by "+incrementField));
//		sqlserverSelectByExampleElement.addElement(incrementFieldIf);
		//part7 ) as _pagination_rownumber,
//		sqlserverSelectByExampleElement.addElement(new TextElement(") as _pagination_rownumber,"));
		//part8 <include refid="Base_Column_List" />
//		sqlserverSelectByExampleElement.addElement(elements.get(2));
		//part9 from table_name
//		sqlserverSelectByExampleElement.addElement(elements.get(3));
		//part10 <if test="_parameter != null"><include refid="Example_Where_Clause" /></if>
//		sqlserverSelectByExampleElement.addElement(elements.get(4));
		//part11 <include refid="SqlServerDialectSuffix" />
//		XmlElement suffix = new XmlElement("include");
//		suffix.addAttribute(new Attribute("refid", "SqlServerDialectSuffix"));
//		sqlserverSelectByExampleElement.addElement(suffix);
		
		return element;
	}

	/**
	 * 在xml的插入配置增加查询序列配置
	 * 
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 下午12:00:37
	 */
	@Override
	public void adaptInsertSelective(XmlElement element, IntrospectedTable introspectedTable) {

	}
}
