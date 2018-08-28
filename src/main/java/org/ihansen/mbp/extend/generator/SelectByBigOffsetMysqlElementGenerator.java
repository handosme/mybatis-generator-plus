package org.ihansen.mbp.extend.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author wus
 */
public class SelectByBigOffsetMysqlElementGenerator implements AbstractXmlElementGenerator {

    public void addElements(XmlElement parentElement,IntrospectedTable introspectedTable) {
        if (introspectedTable.getPrimaryKeyColumns()==null||introspectedTable.getPrimaryKeyColumns().size()!=1){
            return;
        }
        String primaryKeyName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();
        XmlElement selectByBigOffsetElement = new XmlElement("select");
        selectByBigOffsetElement.addAttribute(new Attribute("id", SelectByBigOffsetMethodGenerator.METHOD_NAME));
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
        parentElement.addElement(selectByBigOffsetElement);
    }
}
