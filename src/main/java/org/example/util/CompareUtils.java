package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.SystemException;
import org.example.model.UpdateDebtor;
import org.example.model.database.Debtor;
import org.example.type.Codes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CompareUtils {
    public static Debtor getUpdatedDebtor(Debtor oldDebtor, Debtor newDebtor) throws SystemException {

        try {
            BeanUtils.copyProperties(newDebtor, oldDebtor, getNullPropertyNames(newDebtor));
        } catch (Exception e) {
            log.error("Algo de errado aconteceu na comparação dos dados", e);
            throw new SystemException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na comparação dos dados");
        }

        return oldDebtor;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || srcValue.equals("")) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
