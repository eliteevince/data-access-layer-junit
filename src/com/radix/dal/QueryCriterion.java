package com.radix.dal;

public class QueryCriterion {

    private String condition;
    private String schemaName;
    private String updateColumn;

    public QueryCriterion(String schemaName) {
        this.schemaName = schemaName;
    }

    public QueryCriterion(String schemaName, String condition) {
        this.schemaName = schemaName;
        this.condition = condition;
    }

    public QueryCriterion(String schemaName, String updateColumn, String condition) {
        this(schemaName, condition);
        this.updateColumn = updateColumn;
    }

    public QueryCriterion() {
    }

    public String getQueryCondition() {
        return this.condition;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getUpdateColumn() {
        return updateColumn;
    }

}
