package org.ihansen.mbp.extend.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

/**
 * @author wus
 */
public class SelectByBigOffsetMethodGenerator implements AbstractJavaMapperMethodGenerator {

    public static final String METHOD_NAME = "selectByBigOffset";

    public void addMethod(Interface interfaze,IntrospectedTable introspectedTable) {
        Method method = new Method();
        // 1.设置方法可见性
        method.setVisibility(JavaVisibility.PUBLIC);
        // 2.设置返回值类型
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType paramListType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        returnType.addTypeArgument(paramListType);
        method.setReturnType(returnType);
        // 3.设置方法名
        method.setName(METHOD_NAME);
        // 4.设置方法入参
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        method.addParameter(new Parameter(type, "example"));
        interfaze.addMethod(method);
    }

}
