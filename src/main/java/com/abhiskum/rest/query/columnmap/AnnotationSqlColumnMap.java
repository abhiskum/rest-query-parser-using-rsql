package com.abhiskum.rest.query.columnmap;

import com.abhiskum.rest.query.annotation.Column;
import com.abhiskum.rest.query.annotation.JoinTable;
import com.abhiskum.rest.query.annotation.Table;
import com.abhiskum.rest.query.model.SqlColumnMetadata;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class AnnotationSqlColumnMap implements SqlColumnMap {

    private Map<String, SqlColumnMetadata> columns;

    public AnnotationSqlColumnMap(Class<?> klass) {
        columns = new HashMap<>();
        Table table = klass.getAnnotation(Table.class);
        Field[] fields = klass.getDeclaredFields();
        Set<Class> parentKlasses = new HashSet<>();
        buildColumnsMapping("", parentKlasses, klass, fields, table.alias());
    }

    @Override
    public Map<String, SqlColumnMetadata> getSqlColumnMap() {
        return columns;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AnnotationSqlColumnMap.class.getSimpleName() + "[", "]").add(
            "columns=" + columns).toString();
    }

    private void buildColumnsMapping(String parentFieldName, Set<Class> parentKlasses, Class<?> klass,
        Field[] fields, String tableAlias) {

        if (!parentFieldName.equalsIgnoreCase("")) {
            parentFieldName = parentFieldName + ".";
        }

        boolean isTableAliasBlank = false;
        if (tableAlias == null || tableAlias.trim().equalsIgnoreCase("")) {
            isTableAliasBlank = true;
        }

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            JoinTable joinTable = field.getAnnotation(JoinTable.class);

            Class<?> type = field.getType();

            if (column != null && joinTable == null) {
                columns.put(parentFieldName + field.getName(), new SqlColumnMetadata(
                    isTableAliasBlank ? column.name() : tableAlias + "." + column.name(), type));
                continue;
            }

            if (column != null && joinTable != null) {
                columns.put(parentFieldName + field.getName(), new SqlColumnMetadata(isTableAliasBlank ?
                    column.name() :
                    tableAlias + "_" + joinTable.alias() + "." + column.name(), type));
                continue;
            }

            if (joinTable != null) {
                if (Collection.class.isAssignableFrom(type)) {
                    type = (Class<?>) ((ParameterizedType) field.getAnnotatedType()
                        .getType()).getActualTypeArguments()[0];
                    System.out.println("debug");
                }
                if (parentKlasses.contains(type)) {
                    continue;
                }
                parentKlasses.add(type);
                String alias = joinTable.alias();
                Field[] joinColumns = type.getDeclaredFields();

                buildColumnsMapping(parentFieldName + field.getName(), parentKlasses, type, joinColumns,
                    isTableAliasBlank ? alias : tableAlias + "_" + alias);
                parentKlasses.remove(type);
                continue;
            }

        }
    }
}
