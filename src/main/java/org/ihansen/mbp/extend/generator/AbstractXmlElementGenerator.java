package org.ihansen.mbp.extend.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author wus
 */
public interface AbstractXmlElementGenerator {
    void addElements(XmlElement parentElement, IntrospectedTable introspectedTable);
}
