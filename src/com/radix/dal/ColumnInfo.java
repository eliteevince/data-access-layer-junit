package com.radix.dal;

public class ColumnInfo {

    private String name;
    private String updateTo;
    private ColumnType type;
    private int length;

    public ColumnInfo(String name) {
        this.name = name;
    }
    
    public ColumnInfo(String name, ColumnType type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

    public ColumnInfo(String name, String updateTo, ColumnType type, int length) {
        this(name, type, length);
        this.updateTo = updateTo;
    }

    public String getName() {
        return this.name;
    }

    public ColumnType getType() {
        return this.type;
    }

    public int getLength() {
        return this.length;
    }

    public String getUpdateTo() {
        return null == updateTo ? this.name : updateTo;
    }

}
