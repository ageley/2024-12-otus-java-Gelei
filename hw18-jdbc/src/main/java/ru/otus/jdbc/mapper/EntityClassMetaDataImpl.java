package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    @SuppressWarnings("unchecked")
    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.name = entityClass.getSimpleName();
        this.constructor = Arrays.stream(entityClass.getDeclaredConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .filter(declaredConstructor -> declaredConstructor.getParameterCount() != 0)
                .filter(declaredConstructor -> Modifier.isPublic(declaredConstructor.getModifiers()))
                .map(declaredConstructor -> (Constructor<T>) declaredConstructor)
                .orElseThrow(() -> new ConstructorNotFoundException(this.name));
        this.allFields = Arrays.asList(entityClass.getDeclaredFields());
        this.idField = this.allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IdFieldNotFoundException(this.name));
        this.fieldsWithoutId =
                this.allFields.stream().filter(field -> !idField.equals(field)).toList();

        List<Method> gettersWithoutIdList = new ArrayList<>();
        Method idMethod = null;

        for (Method method : entityClass.getDeclaredMethods()) {
            if (isGetter(method)) {
                if (isIdGetter(method, idField)) {
                    idMethod = method;
                } else {
                    gettersWithoutIdList.add(method);
                }
            }
        }

        this.gettersWithoutId = gettersWithoutIdList;

        List<Method> gettersList = new ArrayList<>(this.gettersWithoutId);
        gettersList.add(idMethod);
        this.getters = gettersList;
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
