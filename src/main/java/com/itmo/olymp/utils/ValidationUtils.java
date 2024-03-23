package com.itmo.olymp.utils;

import com.itmo.olymp.exception.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {

    public void validateIntegerOrThrow(Integer value, int lowerBound, int upperBound, String fieldName) {
        if (value == null || value < lowerBound || value > upperBound) {
            throw new ValidationException(lowerBound, upperBound, fieldName);
        }
    }

    public void validateStringOrThrow(String value, int upperBound, String fieldName) {
        if (value == null || value.isEmpty() || value.length() > upperBound) {
            throw new ValidationException(upperBound, fieldName);
        }
    }

}
