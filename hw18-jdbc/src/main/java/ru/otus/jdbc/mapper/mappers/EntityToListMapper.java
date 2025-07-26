package ru.otus.jdbc.mapper.mappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import ru.otus.jdbc.mapper.mappers.exception.EntityMappingException;

public class EntityToListMapper<T> implements BiFunction<T, List<Method>, List<Object>> {

    @Override
    public List<Object> apply(T entity, List<Method> getters) {
        List<Object> fieldValues = new ArrayList<>();

        for (Method method : getters) {
            try {
                fieldValues.add(method.invoke(entity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new EntityMappingException(e);
            }
        }

        return fieldValues;
    }
}
