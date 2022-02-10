package com.abhiskum.rest.query.test;

import com.abhiskum.rest.query.columnmap.AnnotationSqlColumnMap;
import com.abhiskum.rest.query.columnmap.SqlColumnMap;
import com.abhiskum.rest.query.model.SqlColumnMetadata;
import com.abhiskum.rest.query.model.User;
import com.abhiskum.rest.query.parser.RSQLParserBuilder;
import com.abhiskum.rest.query.visitor.BaseRSQLVisitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cz.jirutka.rsql.parser.ast.Node;

import java.util.HashMap;
import java.util.Map;

public class RSQLParserTest {

    @DisplayName("Test query parsing with annotated classes")
    @Test
    public void testParsingWithAnnotatedClasses() {
        String query = "name=in=(abhishek,avinash) and (addresses.country.code=='IN' or addresses.country.code== US) and ( age>=40 or indian == false)";
        String expectedWhereClause = "( USER.USER_NAME in ('abhishek','avinash') and ( USER_ADDRESS_COUNTRY.CODE = 'IN' or USER_ADDRESS_COUNTRY.CODE = 'US' )  and ( USER.AGE >= 40 or USER.IS_INDIAN = 0 )  )";
        Node node = RSQLParserBuilder.newRSQLParser().parse(query);
        SqlColumnMap annotationSqlColumnMap = new AnnotationSqlColumnMap(User.class);
        System.out.println(annotationSqlColumnMap.toString());
        String whereClause = node.accept(new BaseRSQLVisitor(annotationSqlColumnMap));
        Assertions.assertEquals(expectedWhereClause.trim(), whereClause.trim());
    }

    @DisplayName("Test query parsing without annotated classes")
    @Test
    public void testParsingWithoutAnnotatedClasses() {
        String query = "name=in=(abhishek,avinash) and (addresses.country.code=='IN' or addresses.country.code== US) and ( age>=40 or indian == false)";
        String expectedWhereClause = "( USER.USER_NAME in ('abhishek','avinash') and ( USER_ADDRESS_COUNTRY.CODE = 'IN' or USER_ADDRESS_COUNTRY.CODE = 'US' )  and ( USER.AGE >= 40 or USER.IS_INDIAN = 0 )  )";
        Node node = RSQLParserBuilder.newRSQLParser().parse(query);
        SqlColumnMap sqlColumnMap = new SqlColumnMap() {

            @Override
            public Map<String, SqlColumnMetadata> getSqlColumnMap() {
                Map<String, SqlColumnMetadata> sqlColumnMap = new HashMap<>();
                sqlColumnMap.put("name", new SqlColumnMetadata("USER.USER_NAME", String.class));
                sqlColumnMap.put("age", new SqlColumnMetadata("USER.AGE", int.class));
                sqlColumnMap.put("indian", new SqlColumnMetadata("USER.IS_INDIAN", boolean.class));
                sqlColumnMap.put("addresses.country.code",
                    new SqlColumnMetadata("USER_ADDRESS_COUNTRY.CODE", String.class));
                return sqlColumnMap;
            }
        };
        String whereClause = node.accept(new BaseRSQLVisitor(sqlColumnMap));
        Assertions.assertEquals(expectedWhereClause.trim(), whereClause.trim());
    }


}
