package ru.otus.jdbc.mapper.exception;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException(String className) {
        super("No constructors found in " + className);
    }
}
