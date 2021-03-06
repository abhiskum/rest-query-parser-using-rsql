package com.abhiskum.rest.query.operator.handler;

import java.util.List;

public interface OperatorHandler {

    String handle(String columnName, String value, Class type);

    default String handle(String columnName, List<String> value, Class type) {
        return handle(columnName, value.get(0), type);
    }

    default String parseValue(String value, Class type) {

        if (String.class == type) {
            return "'" + value + "'";
        }
        else if (Boolean.class == type || boolean.class == type) {
            Boolean aBoolean = Boolean.valueOf(value);
            return aBoolean ? "1" : "0";
        }
        else {
            return value;
        }

    }

}
