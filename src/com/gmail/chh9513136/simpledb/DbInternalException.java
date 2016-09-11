package com.gmail.chh9513136.simpledb;

public class DbInternalException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DbInternalException() {
        super();
    }

    public DbInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbInternalException(String message) {
        super(message);
    }

    public DbInternalException(Throwable cause) {
        super(cause);
    }

}
