package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class ProcessorThrowAtEvenSecondTest {
    @Test
    void throwAtEvenSecondTest() {
        // given
        var message = new Message.Builder(1L).build();

        var processor = new ProcessorThrowAtEvenSecond(() -> LocalDateTime.of(2025, 4, 7, 10, 15, 2));

        // then
        assertThrows(RuntimeException.class, () -> processor.process(message));
    }

    @Test
    void doesNotThrowAtOddSecondTest() {
        // given
        var message = new Message.Builder(1L).build();

        var processor = new ProcessorThrowAtEvenSecond(() -> LocalDateTime.of(2025, 4, 7, 10, 15, 1));

        // then
        assertDoesNotThrow(() -> processor.process(message));
    }
}
