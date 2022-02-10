# Rest Query Parser Using RSQL 

This code generate where clause using custom visitor and RSQL library. 

## Usage 
### Using annotated classes 
1. Annotate classes with @Table, @Column and @JoinColumn 
2. Create AnnotationSqlColumnMap class 
3. Use AnnotationSqlColumnMap object to create BaseRSQLVisitor


    Node node = RSQLParserBuilder.newRSQLParser().parse(query);
    SqlColumnMap annotationSqlColumnMap = new AnnotationSqlColumnMap(User.class);
    String whereClause = node.accept(new BaseRSQLVisitor(annotationSqlColumnMap));
    


### Using own sql column and field mapping
1. Create implementation of SqlColumnMap
2. Use SqlColumnMap implementation object to create BaseRSQLVisitor


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
