package org.ihansen.mbp.extend.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.TableConfiguration;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author wus
 */
public class UpdateByOptimisticLockMethodGenerator implements AbstractJavaMapperMethodGenerator {

    public static final String METHOD_NAME = "updateByOptimisticLock";

    @Override
    public void addMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        Properties properties = tableConfiguration.getProperties();
        String versionField = properties.getProperty("versionField");
        if (versionField == null || versionField == "") {
            return;
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        FullyQualifiedJavaType parameterType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = new FullyQualifiedJavaType(introspectedTable.getRecordWithBLOBsType());
        } else {
            parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        }
        importedTypes.add(parameterType);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(METHOD_NAME);
        method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }
}
