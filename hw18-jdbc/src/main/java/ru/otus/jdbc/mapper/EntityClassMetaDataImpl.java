package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.jdbc.mapper.exception.ConstructorNotFoundException;
import ru.otus.jdbc.mapper.exception.IdFieldNotFoundException;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;
    private final List<Method> getters;
    private final List<Method> gettersWithoutId;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.name = entityClass.getSimpleName();
        this.allFields = Arrays.asList(entityClass.getDeclaredFields());
        this.idField = this.allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IdFieldNotFoundException(this.name));
        this.fieldsWithoutId =
                this.allFields.stream().filter(field -> !idField.equals(field)).toList();
        this.constructor = fetchConstructor(entityClass, this.allFields, this.name);
        this.gettersWithoutId = new ArrayList<>();
        this.getters = new ArrayList<>();
        initGetters(entityClass, this.idField, this.gettersWithoutId, this.getters);
    }

    private Constructor<T> fetchConstructor(Class<T> entityClass, List<Field> allFields, String name) {
        try {
            return entityClass.getConstructor(
                    allFields.stream().map(Field::getType).toArray(Class<?>[]::new));
        } catch (NoSuchMethodException e) {
            throw new ConstructorNotFoundException(name, e);
        }
    }

    private boolean isGetter(Method method) {
        String methodName = method.getName();
        return Modifier.isPublic(method.getModifiers())
                && method.getParameterCount() == 0
                && (methodName.startsWith("get") || methodName.startsWith("is") || methodName.startsWith("are"));
    }

    private boolean isIdGetter(Method method, Field id) {
        String idFieldName = id.getName();
        String idGetterEnding = idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
        String methodName = method.getName();
        return isGetter(method)
                && (methodName.equals("get" + idGetterEnding)
                        || methodName.equals("is" + idGetterEnding)
                        || methodName.equals("are" + idGetterEnding));
    }

    private void initGetters(Class<T> entityClass, Field idField, List<Method> gettersWithoutId, List<Method> getters) {
        Method idGetter = null;

        for (Method method : entityClass.getDeclaredMethods()) {
            if (isGetter(method)) {
                if (isIdGetter(method, idField)) {
                    idGetter = method;
                } else {
                    gettersWithoutId.add(method);
                }
            }
        }

        getters.addAll(gettersWithoutId);
        getters.add(idGetter);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    @Override
    public List<Method> getGetters() {
        return getters;
    }

    @Override
    public List<Method> getGettersWithoutId() {
        return gettersWithoutId;
    }
}
