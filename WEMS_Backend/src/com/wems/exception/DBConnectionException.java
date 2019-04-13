package com.wems.exception;

public class DBConnectionException extends Exception {
    
    Exception ex;
    
    public DBConnectionException (Exception e) {
        ex = e;
    }
    
    public String toString () {
        return "Database Error: " + ex.toString ();
    }
}
