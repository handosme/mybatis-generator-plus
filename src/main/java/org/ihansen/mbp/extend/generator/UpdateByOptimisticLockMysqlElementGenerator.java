package org.ihansen.mbp.extend.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.TableConfiguration;

import java.util.List;
import java.util.Properties;

/**
 * @author wus
 */
public class UpdateByOptimisticLockMysqlElementGenerator implements AbstractXmlElementGenerator {
    @Override
    public void addElements(XmlElement parentElement, IntrospectedTable introspectedTable) {
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        Properties properties = tableConfiguration.getProperties();
        String versionField = properties.getProperty("versionField");
        if (versionField == null || versionField == "") {
            return;
        }
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", UpdateByOptimisticLockMethodGenerator.METHOD_NAME)); //$NON-NLS-1$
        String parameterType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = introspectedTable.getRecordWithBLOBsType();
        } else {
            parameterType = introspectedTable.getBaseRecordType();
        }
        answer.addAttribute(new Attribute("parameterType", parameterType));
        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);
        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append(',');
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        dynamicElement.addElement(new TextElement(String.format("%s = %s+1,",versionField,versionField)));//version = version+1,

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getBaseColumns();
        IntrospectedColumn versionIntrospectedColumn = null;
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (versionField.equals(introspectedColumn.getActualColumnName())){
                versionIntrospectedColumn = introspectedColumn;
            }
        }
        answer.addElement(new TextElement(String.format("and %s = #{%s,jdbcType=%s}", versionField, versionIntrospectedColumn.getJavaProperty(), versionIntrospectedColumn.getJdbcTypeName())));//and version = #{id,jdbcType=INTEGER}
        parentElement.addElement(answer);
    }
}
