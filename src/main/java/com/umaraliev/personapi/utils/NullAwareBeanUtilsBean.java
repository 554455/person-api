package com.umaraliev.personapi.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class NullAwareBeanUtilsBean<T>{

    public T copyNonNullProperties(T target, T in) {
        if (in == null || target == null || !target.getClass().isAssignableFrom(in.getClass())) return null;

        final BeanWrapper src = new BeanWrapperImpl(in);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            try {
                property.setAccessible(true);
                Object providedObject = src.getPropertyValue(property.getName());
                if (providedObject != null) {
                    if (providedObject instanceof Collection<?>) {
                        trg.setPropertyValue(
                                property.getName(),
                                new ArrayList<>((Collection<?>) providedObject));
                    } else {
                        trg.setPropertyValue(property.getName(), providedObject);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to copy property: " + property.getName(), e);
            }
        }
        return target;
    }
}