package ru.otus.jdbc.mapper.mappers;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import ru.otus.jdbc.mapper.mappers.exception.EntityMappingException;

@RequiredArgsConstructor
public class ResultSetToEntityMapper<T> implements Function<ResultSet, T> {
    private final ConstructorFieldsPair<T> pair;

    @Override
    public T apply(ResultSet rs) {
        try {
            return pair.constructor()
                    .newInstance(pair.fields().stream()
                            .map(field -> getColumnValue(rs, field.getName()))
                            .toList()
                            .toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EntityMappingException(e);
        }
    }

    private Object getColumnValue(ResultSet rs, String columnName) {
        try {
            return rs.getObject(columnName);
        } catch (SQLException e) {
            throw new EntityMappingException(e);
        }
    }
}
