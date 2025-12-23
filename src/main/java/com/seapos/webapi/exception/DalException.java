package com.seapos.webapi.exception;

public class DalException extends RuntimeException {

//    public DalException(String message, Throwable cause) {
//        super(message, cause);
//    }
private final String code;

    // Business error without cause
    public DalException(String code, String message) {
        super(message);
        this.code = code;
    }

    // Business error with cause
    public DalException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
