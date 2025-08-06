package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final String tableName;
    private final String idField;
    private final String allFields;
    private final String fieldsWithoutId;
    private final String fieldsPlaceholders;
    private final String fieldsAssignments;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaDataClient) {
        this.tableName = entityClassMetaDataClient.getName();
        this.idField = entityClassMetaDataClient.getIdField().getName();
        this.allFields = String.join(
                ", ",
                entityClassMetaDataClient.getAllFields().stream()
                        .map(Field::getName)
                        .toList());
        this.fieldsWithoutId = String.join(
                ", ",
                entityClassMetaDataClient.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .toList());
        this.fieldsAssignments = String.join(
                "and ",
                entityClassMetaDataClient.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .map(name -> name + " = ? ")
                        .toList());
        this.fieldsPlaceholders = String.join(
                ", ",
                Collections.nCopies(
                        entityClassMetaDataClient.getFieldsWithoutId().size(), "?"));
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select %s from %s;", allFields, tableName);
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select %s from %s where %s = ?;", allFields, tableName, idField);
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s (%s) values (%s);", tableName, fieldsWithoutId, fieldsPlaceholders);
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s = ?;", tableName, fieldsAssignments, idField);
    }
}
