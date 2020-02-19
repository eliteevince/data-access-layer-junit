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
public class PersistenceException extends Exception {

    public PersistenceException() {
    }

    public PersistenceException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

    public PersistenceException(String msg, Exception e) {
        super(msg, e.getCause());
    }

}
