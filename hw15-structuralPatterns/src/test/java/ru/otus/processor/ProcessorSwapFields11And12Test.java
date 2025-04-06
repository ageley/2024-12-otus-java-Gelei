package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class ProcessorSwapFields11And12Test {

    @Test
    void swapFieldsTest() {
        // given
        var message =
                new Message.Builder(1L).field11("field11").field12("field12").build();

        var processor = new ProcessorSwapFields11And12();

        // when
        var result = processor.process(message);

        // then
        assertEquals(message.getField12(), result.getField11());
        assertEquals(message.getField11(), result.getField12());
    }
}
