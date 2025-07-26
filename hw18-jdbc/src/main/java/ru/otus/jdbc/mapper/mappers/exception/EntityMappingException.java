package ru.otus.jdbc.mapper.mappers.exception;

public class EntityMappingException extends RuntimeException {
    public EntityMappingException(Throwable e) {
        super(e);
    }
}
