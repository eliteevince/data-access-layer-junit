/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radix.dal;

/**
 *
 * @author Meet
 */
public interface PersistenceManager {

    public void createSchemaObject(String objectName, ColumnInfo[] columns) throws PersistenceException;

    public void updateSchemaObject(String objectName, ColumnInfo[] columns) throws PersistenceException;

    public ResultSet executeNamedQuery(String name) throws PersistenceException;

    public ResultSet executeQueryCriterion(QueryCriterion query) throws PersistenceException;

    public int insertRecords(String schemaObject, ResultSet values) throws PersistenceException;

    public int deleteRecords(QueryCriterion deleteQuery) throws PersistenceException;

    public int updateRecords(QueryCriterion queryCriterion) throws PersistenceException;

}
