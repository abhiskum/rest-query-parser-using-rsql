package com.abhiskum.rest.query.columnmap;

import com.abhiskum.rest.query.model.SqlColumnMetadata;

import java.util.Map;

public interface SqlColumnMap {

    Map<String, SqlColumnMetadata> getSqlColumnMap();
}
