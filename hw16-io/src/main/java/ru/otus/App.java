package ru.otus;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import ru.otus.dataprocessor.FileSerializer;
import ru.otus.dataprocessor.Processor;
import ru.otus.dataprocessor.ProcessorAggregator;
import ru.otus.dataprocessor.ResourcesFileLoader;
import ru.otus.dataprocessor.Serializer;
import ru.otus.model.Measurement;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        URI appLocation =
                App.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        File appDir = new File(appLocation).getParentFile();

        String inputDataFileName = "inputData.json";
        String outputDataFileName = "outputData.json";
        String fullOutputFilePath = String.format("%s%s%s", appDir, File.separator, outputDataFileName);

        ResourcesFileLoader loader = new ResourcesFileLoader(inputDataFileName);
        Processor processor = new ProcessorAggregator();
        Serializer serializer = new FileSerializer(fullOutputFilePath);

        List<Measurement> loadedMeasurements = loader.load();
        System.out.println("Input file relative path: " + loader.getFullInputFilePath());
        System.out.println("Input file parsed content: " + loadedMeasurements);

        Map<String, Double> aggregatedMeasurements = processor.process(loadedMeasurements);
        System.out.println("Output file absolute path: " + fullOutputFilePath);
        System.out.println("Output file prepared content: " + aggregatedMeasurements);

        serializer.serialize(aggregatedMeasurements);
    }
}
