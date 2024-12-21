package org.example.util;

import org.example.exception.BusinessException;
import org.example.type.Codes;

public class StringUtils {
    public static String isEmpty(String field, String str) throws BusinessException {
        if (str == null || str.isEmpty()) {
            throw new BusinessException(Codes.INVALID_PARAMETERS, String.format("O campo %s não pode ser vazio", field));
        }
        return str;
    }
}
