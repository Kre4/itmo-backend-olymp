package com.itmo.olymp.utils;

import com.itmo.olymp.exception.RequiredFieldException;
import com.itmo.olymp.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {

    public void validateIntegerOrThrow(Integer value, int lowerBound, int upperBound, String fieldName) {
        validateNotNull(value, fieldName);
        if (value < lowerBound || value > upperBound) {
            throw new ValidationException(lowerBound, upperBound, fieldName);
        }
    }

    public void validateStringOrThrow(String value, int upperBound, String fieldName) {
        validateNotNull(value, fieldName);
        if (value.isEmpty() || value.length() > upperBound) {
            throw new ValidationException(upperBound, fieldName);
        }
    }

    public <T> void validateNotNull(T value, String fieldName) {
        if (value == null) {
            throw new RequiredFieldException(fieldName);
        }
    }

}
