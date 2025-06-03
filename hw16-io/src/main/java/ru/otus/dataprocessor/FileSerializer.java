package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import lombok.NonNull;

public class FileSerializer implements Serializer {

    private final String fileName;
    private final ObjectWriter writer;

    public FileSerializer(@NonNull String fileName) {
        this.fileName = fileName;
        writer = new JsonMapper().writer();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        File file = new File(fileName);

        try {
            writer.writeValue(file, data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
