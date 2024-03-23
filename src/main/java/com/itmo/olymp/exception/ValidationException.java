package com.itmo.olymp.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(int lowerBound, int upperBound, String fieldName) {
        super(String.format("%s must be in bounds [%d;%d]", fieldName, lowerBound, upperBound));
    }

    public ValidationException(int stringUpperBound, String fieldName) {
        super(String.format("%s must have length [0;%d]", fieldName, stringUpperBound));
    }
}
