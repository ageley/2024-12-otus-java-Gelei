package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private static final String RESOURCES_ROOT = "/";

    @Getter
    private final String fullInputFilePath;

    private final ObjectReader reader;

    public ResourcesFileLoader(@NonNull String fileName) {
        this.fullInputFilePath = RESOURCES_ROOT + fileName;
        this.reader = new JsonMapper().readerForListOf(Measurement.class);
    }

    @Override
    public List<Measurement> load() {
        try (InputStream inputStream = getClass().getResourceAsStream(fullInputFilePath)) {

            if (inputStream == null) {
                throw new FileProcessException("Resource not found: " + fullInputFilePath);
            }

            return reader.readValue(inputStream);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
