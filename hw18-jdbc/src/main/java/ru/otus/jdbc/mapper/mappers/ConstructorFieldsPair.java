package ru.otus.jdbc.mapper.mappers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public record ConstructorFieldsPair<T>(Constructor<T> constructor, List<Field> fields) {}
