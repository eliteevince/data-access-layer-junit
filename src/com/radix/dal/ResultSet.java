package com.radix.dal;

public class ResultSet {

    private Object[][] data;
    private ColumnInfo[] columns;

    public ColumnInfo[] getColumns() {
        return this.columns;
    }

    public Object[][] getData() {
        return this.data;
    }

    public void setColumns(ColumnInfo[] columns) {
        this.columns = columns;
    }

    public void setObjects(Object[][] data) {
        this.data = data;
    }

}
