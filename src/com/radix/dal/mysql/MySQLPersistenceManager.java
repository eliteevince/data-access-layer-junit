package com.radix.dal.mysql;

import com.radix.dal.ColumnInfo;
import com.radix.dal.PersistenceException;
import com.radix.dal.PersistenceManager;
import com.radix.dal.QueryCriterion;
import com.radix.dal.ResultSet;
import com.radix.dal.SequenceGenerator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MySQLPersistenceManager implements PersistenceManager {

    Connection con;
    final String dbName;

    /**
     * TODO: Create MySQL connection on object creation
     *
     * @throws PersistenceException
     */
    public MySQLPersistenceManager() throws PersistenceException {
        try {
            dbName = "radix";
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName + "?useLegacyDatetimeCode=false&serverTimezone=UTC", "root",
                    "");
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Close connection when its necessary
     *
     * @throws PersistenceException
     */
    public void closeConnection() throws PersistenceException {
        try {
            con.close();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Create table in MySQL database
     *
     * @throws PersistenceException
     */
    @Override
    public void createSchemaObject(String objectName, ColumnInfo[] columns) throws PersistenceException {
        try {
            StringBuilder query = new StringBuilder("CREATE TABLE `").append(dbName).append("`.`");
            query.append(objectName).append("` (seq_id INT(12), ");
            for (ColumnInfo column : columns) {
                query.append("`").append(column.getName()).append("` ");
                switch (column.getType()) {
                    case BOOLEAN:
                        query.append("TINYINT, ");
                        break;
                    case NUMBER:
                        query.append("INT(").append(column.getLength()).append("), ");
                        break;
                    case TEXT:
                        query.append("VARCHAR(").append(column.getLength()).append("), ");
                        break;
                }
            }
            query = query.deleteCharAt(query.lastIndexOf(", ")).append(");");
            con.prepareStatement(query.toString()).execute();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Update table in MySQL database
     *
     * @throws PersistenceException
     */
    @Override
    public void updateSchemaObject(String objectName, ColumnInfo[] columns) throws PersistenceException {
        try {
            StringBuilder query = new StringBuilder("ALTER TABLE `").append(dbName).append("`.`");
            query.append(objectName).append("` ");
            DatabaseMetaData metaData = con.getMetaData();
            for (ColumnInfo column : columns) {

                java.sql.ResultSet columns2 = metaData.getColumns(null, dbName, objectName, column.getName());

                if (columns2.next()) {
                    query.append("CHANGE COLUMN ").append("`").append(column.getName()).append("` ");
                    if (null != column.getUpdateTo()) {
                        query.append("`").append(column.getUpdateTo()).append("` ");
                    }
                } else {
                    query.append("ADD COLUMN ").append("`").append(column.getName()).append("` ");
                }
                switch (column.getType()) {
                    case BOOLEAN:
                        query.append("TINYINT, ");
                        break;
                    case NUMBER:
                        query.append("INT(").append(column.getLength()).append("), ");
                        break;
                    case TEXT:
                        query.append("VARCHAR(").append(column.getLength()).append("), ");
                        break;
                }
            }
            query = query.deleteCharAt(query.lastIndexOf(", ")).append(";");
            con.prepareStatement(query.toString()).execute();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Fetch result using named query
     *
     * @throws PersistenceException
     */
    @Override
    public ResultSet executeNamedQuery(String query) throws PersistenceException {
        try {
            java.sql.ResultSet executeQuery = con.prepareStatement(query).executeQuery();
            return setResultSet(executeQuery);
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Fetch result using criteria
     *
     * @throws PersistenceException
     */
    @Override
    public ResultSet executeQueryCriterion(QueryCriterion queryCriterion) throws PersistenceException {
        try {
            StringBuilder query = new StringBuilder("SELECT * from `" + dbName + "`.`")
                    .append(queryCriterion.getSchemaName()).append("` ");
            if (null != queryCriterion.getQueryCondition()) {
                query.append(" where ").append(queryCriterion.getQueryCondition());
            }
            java.sql.ResultSet executeQuery = con.prepareStatement(query.toString()).executeQuery();
            return setResultSet(executeQuery);
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Utility method for set result set
     *
     * @param resultSet
     * @return
     * @throws PersistenceException
     */
    private ResultSet setResultSet(java.sql.ResultSet resultSet) throws PersistenceException {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ColumnInfo[] cols = new ColumnInfo[columnCount];
            for (int i = 0; i < columnCount; i++) {
                cols[i] = new ColumnInfo(metaData.getColumnName(i + 1));
            }
            resultSet.last();
            int counter = resultSet.getRow();
            Object[][] objectses = new Object[counter][];
            int col = 0;
            resultSet.beforeFirst();
            while (resultSet.next()) {
                objectses[col] = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    objectses[col][i] = resultSet.getObject(i + 1);
                }
                col++;
            }
            ResultSet resultSetData = new ResultSet();
            resultSetData.setColumns(cols);
            resultSetData.setObjects(objectses);
            return resultSetData;
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Insert data into given table
     *
     * @param schemaObject
     * @param values
     * @return
     * @throws PersistenceException
     */
    @Override
    public synchronized int insertRecords(String schemaObject, ResultSet values) throws PersistenceException {
        try {
            SequenceGenerator sequenceGenerator = new SequenceGenerator(con, schemaObject);
            StringBuilder query = new StringBuilder("insert into `" + dbName + "`.`");
            query.append(schemaObject).append("` (seq_id,");
            ColumnInfo[] columns = values.getColumns();

            for (ColumnInfo column : columns) {
                query.append("`").append(column.getName()).append("`, ");
            }
            query = query.deleteCharAt(query.lastIndexOf(", ")).append(") VALUES ");
            Object[][] data = values.getData();
            int nextSequence = sequenceGenerator.getNextSequence();
            for (Object[] objects : data) {
                query.append("(").append(nextSequence++).append(", '").append(objects[0]).append("', '").append(objects[1]).append("', ")
                        .append(objects[2]).append("), ");
            }
            query = query.deleteCharAt(query.lastIndexOf(", ")).append(";");
            return con.prepareStatement(query.toString()).executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    /**
     * TODO: Delete record from given table name
     *
     * @throws PersistenceException
     */
    @Override
    public int deleteRecords(QueryCriterion deleteQuery) throws PersistenceException {

        try {
            String query = "DELETE from `" + dbName + "`.`" + deleteQuery.getSchemaName() + "` where "
                    + deleteQuery.getQueryCondition();
            return con.prepareStatement(query).executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

    }

    /**
     * TODO: Update data into given table
     *
     * @throws PersistenceException
     */
    @Override
    public int updateRecords(QueryCriterion queryCriterion) throws PersistenceException {

        try {
            String query = "UPDATE `" + dbName + "`.`" + queryCriterion.getSchemaName() + "` SET "
                    + queryCriterion.getUpdateColumn() + " where " + queryCriterion.getQueryCondition();
            return con.prepareStatement(query).executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

    }

}
