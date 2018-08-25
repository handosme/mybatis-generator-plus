package org.ihansen.mbp.extend.dbSupport;

import java.util.List;

import org.ihansen.mbp.extend.DBSupport;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

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
        selectByBigOffset(document,introspectedTable);
    }

    private void selectByBigOffset(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.getPrimaryKeyColumns()==null||introspectedTable.getPrimaryKeyColumns().size()!=1){
            return;
        }
        String primaryKeyName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        XmlElement selectByBigOffsetElement = new XmlElement("select");
        selectByBigOffsetElement.addAttribute(new Attribute("id", "selectByBigOffset"));
        selectByBigOffsetElement.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
        selectByBigOffsetElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        selectByBigOffsetElement.addElement(new TextElement("select"));
        //.<if test="distinct" >
        //    distinct
        // </if>
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        selectByBigOffsetElement.addElement(ifElement);
        //.<include refid="Base_Column_List" />
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid","Base_Column_List"));
        selectByBigOffsetElement.addElement(includeElement);
        selectByBigOffsetElement.addElement(new TextElement("from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        //   <if test="_parameter != null" >
        //      <include refid="Example_Where_Clause" />
        //      <choose>
        //        <when test="oredCriteria != null and oredCriteria.size()>0 and criteria.valid" >
        //          and
        //        </when>
        //        <otherwise>
        //          where
        //        </otherwise>
        //      </choose>
        //      id &lt;=(select id from user_visit_log
        //      <if test="_parameter != null" >
        //        <include refid="Example_Where_Clause" />
        //      </if>
        //      order by id desc limit #{offset},1) order by id desc limit #{limit}
        //    </if>
        ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test","_parameter != null"));
        includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid","Example_Where_Clause"));
        ifElement.addElement(includeElement);
        XmlElement chooseElement = new XmlElement("choose");
        XmlElement whenElement = new XmlElement("when");
        whenElement.addAttribute(new Attribute("test","oredCriteria != null and oredCriteria.size()>0 and criteria.valid"));
        whenElement.addElement(new TextElement("and"));
        XmlElement otherwiseElement = new XmlElement("otherwise");
        otherwiseElement.addElement(new TextElement("where"));
        chooseElement.addElement(whenElement);
        chooseElement.addElement(otherwiseElement);
        ifElement.addElement(chooseElement);
        ifElement.addElement(new TextElement(primaryKeyName+" &lt;=(select "+primaryKeyName+" from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        XmlElement ifElement2 = new XmlElement("if");
        ifElement2.addAttribute(new Attribute("test","_parameter != null"));
        includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid","Example_Where_Clause"));
        ifElement2.addElement(includeElement);
        ifElement.addElement(ifElement2);
        ifElement.addElement(new TextElement("order by "+primaryKeyName+" desc limit #{offset},1) order by "+primaryKeyName+" desc limit #{limit}"));
        selectByBigOffsetElement.addElement(ifElement);
        document.getRootElement().addElement(selectByBigOffsetElement);
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
}
