package org.ihansen.mbp.extend.java;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

public class CustomInterface extends Interface {

    public CustomInterface(FullyQualifiedJavaType type) {
        super(type);
    }

    public CustomInterface(String type) {
        super(type);
    }


}
