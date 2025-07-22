package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import ru.otus.crm.model.Client;

public class EntityClassMetaDataImpl implements EntityClassMetaData<Client> {
    @Override
    public String getName() {
        return "";
    }

    @Override
    public Constructor<Client> getConstructor() {
        return null;
    }

    @Override
    public Field getIdField() {
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return List.of();
    }
}
