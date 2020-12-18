package com.appdeveloperblog.app.ws.exceptions;

public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = -1902586870976000169L;
    public UserServiceException(String message) {
        super(message);
    }
}
