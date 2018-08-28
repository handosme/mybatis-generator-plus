package org.ihansen.mbp.extend.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;

/**
 * @author wus
 */
public interface AbstractJavaMapperMethodGenerator {
    void addMethod(Interface interfaze, IntrospectedTable introspectedTable);

}
