package ru.otus.jdbc.mapper.exception;

public class IdFieldNotFoundException extends RuntimeException {
    public IdFieldNotFoundException(String className) {
        super("No @Id field found in " + className);
    }
}
