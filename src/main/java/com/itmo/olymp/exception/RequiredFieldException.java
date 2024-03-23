package com.itmo.olymp.exception;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String fieldName) {
        super(fieldName + " cannot be null. Provide a value");
    }
}
