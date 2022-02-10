package com.abhiskum.rest.query.model;

import java.util.StringJoiner;

public class SqlColumnMetadata {

    private String name;
    private Class type;

    public SqlColumnMetadata(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SqlColumnMetadata.class.getSimpleName() + "[", "]").add(
            "name='" + name + "'").add("type=" + type).toString();
    }
}
