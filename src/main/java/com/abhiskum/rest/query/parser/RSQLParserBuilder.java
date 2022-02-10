package com.abhiskum.rest.query.parser;

import com.abhiskum.rest.query.operator.CustomRSQLOperators;

import cz.jirutka.rsql.parser.RSQLParser;

public class RSQLParserBuilder {

    public static RSQLParser newRSQLParser(){
        return new RSQLParser(CustomRSQLOperators.customOperators());
    }

}
