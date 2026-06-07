package com.project.exception;

public class ServletContextService extends RuntimeException {
    public ServletContextService(String message) {
        super(message);
    }
}
