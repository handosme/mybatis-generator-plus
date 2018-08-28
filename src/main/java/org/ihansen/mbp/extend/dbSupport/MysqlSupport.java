package org.ihansen.mbp.extend.dbSupport;

import org.ihansen.mbp.extend.DBSupport;
import org.ihansen.mbp.extend.generator.*;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class MysqlSupport implements DBSupport {
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
        // 1.产生mysql分页的limit条件
        XmlElement paginationSuffixElement = new XmlElement("sql");
        paginationSuffixElement.addAttribute(new Attribute("id", "MysqlDialectSuffix"));
        XmlElement pageEnd = new XmlElement("if");
        pageEnd.addAttribute(new Attribute("test", "offset != null and limit != null"));
        pageEnd.addElement(new TextElement("<![CDATA[ limit #{offset}, #{limit}]]>"));
        paginationSuffixElement.addElement(pageEnd);
        parentElement.addElement(paginationSuffixElement);

        // 2.增加批量插入的xml配置
        addBatchInsertXml(document, introspectedTable);

        // 3.大偏移批量查询
        AbstractXmlElementGenerator elementGenerator = new SelectByBigOffsetMysqlElementGenerator();
        elementGenerator.addElements(document.getRootElement(),introspectedTable);

        // .乐观锁更新
        new UpdateByOptimisticLockMysqlElementGenerator().addElements(document.getRootElement(),introspectedTable);
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
        if (incrementField != null) {
            incrementField = incrementField.toUpperCase();
        }
        StringBuilder dbcolumnsName = new StringBuilder();
        StringBuilder javaPropertyAndDbType = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : columns) {
            String columnName = introspectedColumn.getActualColumnName();
            if (!columnName.toUpperCase().equals(incrementField)) {//不是自增字段的才会出现在批量插入中
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
        XmlElement paginationElement = new XmlElement("include"); //$NON-NLS-1$
        paginationElement.addAttribute(new Attribute("refid", "MysqlDialectSuffix"));
        element.getElements().add(paginationElement);
        return element;
    }

    /**
     * 只插入设置过的字段值,sqlserver空实现即可
     *
     * @author 吴帅
     * @parameter @param element
     * @parameter @param introspectedTable
     * @createDate 2015年9月29日 下午12:00:37
     */
    @Override
    public void adaptInsertSelective(XmlElement element, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addSelectByBigOffsetMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        AbstractJavaMapperMethodGenerator generator = new SelectByBigOffsetMethodGenerator();
        generator.addMethod(interfaze,introspectedTable);
    }

    @Override
    public void addUpdateByOptimisticLockMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        new UpdateByOptimisticLockMethodGenerator().addMethod(interfaze,introspectedTable);
    }
}
