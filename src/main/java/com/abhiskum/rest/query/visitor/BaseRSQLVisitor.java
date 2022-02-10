package com.abhiskum.rest.query.visitor;

import com.abhiskum.rest.query.columnmap.SqlColumnMap;
import com.abhiskum.rest.query.model.SqlColumnMetadata;
import com.abhiskum.rest.query.operator.handler.OperatorHandler;
import com.abhiskum.rest.query.operator.handler.RSQLOperatorHandlers;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

import java.util.stream.Collectors;

public class BaseRSQLVisitor implements RSQLVisitor<String, Object> {

    private SqlColumnMap sqlColumnMap;

    public BaseRSQLVisitor(SqlColumnMap sqlColumnMap) {
        this.sqlColumnMap = sqlColumnMap;
    }

    @Override
    public String visit(AndNode andNode, Object o) {
        return "( " + andNode.getChildren().stream().map(node -> node.accept(this))
            .collect(Collectors.joining(" and ")) + " ) ";
    }

    @Override
    public String visit(OrNode orNode, Object o) {
        return "( " +
            orNode.getChildren().stream().map(node -> node.accept(this)).collect(Collectors.joining(" or ")) +
            " ) ";
    }

    @Override
    public String visit(ComparisonNode node, Object o) {
        ComparisonOperator op = node.getOperator();

        SqlColumnMetadata sqlColumn = sqlColumnMap.getSqlColumnMap().get(node.getSelector());

        if (sqlColumn == null) {
            throw new IllegalArgumentException(String.format("Field '%s' is invalid", node.getSelector()));
        }

        Class type = sqlColumn.getType();

        String queryColumnName = sqlColumn.getName();

        OperatorHandler operatorHandler = RSQLOperatorHandlers.getOperatorHandler(op.getSymbol());
        if (operatorHandler == null) {
            throw new IllegalArgumentException(String.format("Operator '%s' is invalid", op.getSymbol()));
        }

        if (op.isMultiValue()) {
            return operatorHandler.handle(queryColumnName, node.getArguments(), type);
        }
        else {
            return operatorHandler.handle(queryColumnName, node.getArguments().get(0), type);
        }

    }

}
