package ru.otus.processor;

import java.time.LocalDateTime;
import java.util.function.Supplier;
import ru.otus.model.Message;
import ru.otus.processor.exception.EvenNumberOfSecondsException;

public class ProcessorThrowAtEvenSecond implements Processor {
    private final Supplier<LocalDateTime> dateTimeProvider;

    public ProcessorThrowAtEvenSecond(Supplier<LocalDateTime> dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = dateTimeProvider.get().getSecond();

        if (dateTimeProvider.get().getSecond() % 2 != 0) {
            return message;
        }

        throw new EvenNumberOfSecondsException(String.format("%d is an even number of seconds", second));
    }
}
