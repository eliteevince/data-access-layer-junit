package com.radix.dal;

import com.radix.dal.mysql.MySQLPersistenceManager;

public class PersistenceManagerFactory {

    private final static PersistenceManagerFactory factory = new PersistenceManagerFactory();

    private PersistenceManagerFactory() {
    }

    /*
     * TODO : Get every time same factory object as singleton pattern 
     */
    public static PersistenceManagerFactory getInstance() {
        return factory;
    }

    /*
     * TODO : Get persistence object 
     */
    public PersistenceManager getPersistenceManager() throws PersistenceException {
        return new MySQLPersistenceManager();
    }
}
