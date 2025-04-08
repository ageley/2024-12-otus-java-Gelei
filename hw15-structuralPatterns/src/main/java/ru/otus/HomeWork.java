package ru.otus;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.ProcessorSwapFields11And12;
import ru.otus.processor.ProcessorThrowAtEvenSecond;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        var processors = List.of(new ProcessorSwapFields11And12(), new ProcessorThrowAtEvenSecond(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> logger.error(ex.getMessage()));
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var field13Data = new ObjectForMessage();
        field13Data.setData(List.of("field13"));

        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .field13(field13Data)
                .build();

        var result = complexProcessor.handle(message);
        logger.info("result:{}", result);

        complexProcessor.removeListener(historyListener);
    }
}
