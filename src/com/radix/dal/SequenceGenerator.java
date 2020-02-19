package com.radix.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceGenerator {

    private Connection con;
    private String table;

    public SequenceGenerator() {
    }

    public SequenceGenerator(Connection con, String table) {
        this.con = con;
        this.table = table;
    }

    /**
     * TODO : generate next sequence number 
     * @return
     * @throws PersistenceException
     */
    public int getNextSequence() throws PersistenceException {

        try {
            int next = 1;
            ResultSet executeQuery = this.con.prepareStatement("select coalesce(seq_id, 0) from " + this.table + "  order by seq_id desc limit 1").executeQuery();
            if (executeQuery.next()) {
                next = executeQuery.getInt(1) + 1;
            }
            return next;
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }
}
