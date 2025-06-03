package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import lombok.NonNull;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private static final String RESOURCES_ROOT = "/";

    private final String fileName;
    private final ObjectReader reader;

    public ResourcesFileLoader(@NonNull String fileName) {
        this.fileName = fileName;
        reader = new JsonMapper().readerForListOf(Measurement.class);
    }

    @Override
    public List<Measurement> load() {
        File file = new File(getResourceUri());

        try {
            return reader.readValue(file);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

    private URI getResourceUri() {
        String fullInputFilePath = RESOURCES_ROOT + fileName;
        URL resourceUrl = getClass().getResource(fullInputFilePath);

        if (resourceUrl == null) {
            throw new FileProcessException("Resource not found: " + fullInputFilePath);
        }

        try {
            return resourceUrl.toURI();
        } catch (URISyntaxException e) {
            throw new FileProcessException(e);
        }
    }
}
