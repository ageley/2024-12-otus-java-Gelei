package ru.otus.jdbc.mapper;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.mappers.EntityToListMapper;
import ru.otus.jdbc.mapper.mappers.ResultSetToEntityMapper;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final ResultSetToEntityMapper<T> entityMapper;
    private final EntityToListMapper<T> entityToListMapper;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor,
            EntitySQLMetaData entitySQLMetaData,
            ResultSetToEntityMapper<T> resultSetToEntityMapper,
            EntityToListMapper<T> entityToListMapper,
            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityMapper = resultSetToEntityMapper;
        this.entityToListMapper = entityToListMapper;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), entityMapper);
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelectForList(
                connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), entityMapper);
    }

    @Override
    public long insert(Connection connection, T entity) {
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                entityToListMapper.apply(entity, entityClassMetaData.getGettersWithoutId()));
    }

    @Override
    public void update(Connection connection, T entity) {
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getUpdateSql(),
                entityToListMapper.apply(entity, entityClassMetaData.getGetters()));
    }
}
