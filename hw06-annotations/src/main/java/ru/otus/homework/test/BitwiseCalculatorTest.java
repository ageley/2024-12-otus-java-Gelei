package ru.otus.homework.test;

import lombok.extern.slf4j.Slf4j;
import ru.otus.homework.BitwiseCalculator;
import ru.otus.homework.test.util.After;
import ru.otus.homework.test.util.Before;
import ru.otus.homework.test.util.Test;

@Slf4j
public class BitwiseCalculatorTest {

    public BitwiseCalculatorTest() {
        log.info("A new class instance created");
    }

    @Before
    public void doSomethingBeforeTest() {
        log.info("Do something before a test");
    }

    @Before
    public void doSomethingElseBeforeTest() {
        log.info("Do something else before a test");
    }

    @After
    public void doSomethingAfterTest() {
        log.info("Do something after a test");
    }

    @After
    public void doSomethingElseAfterTest() {
        log.info("Do something else before a test");
    }

    @Test
    public void leftShiftIsEqualToMultiplicationByTwo() {
        int value = 5;
        int offset = 3;
        int expectedResult = 40; // value x 2^offset
        int result = BitwiseCalculator.shiftLeft(value, offset);

        if (expectedResult != result) {
            throw new ArithmeticException(String.format("Expected: %d, got: %d", expectedResult, result));
        }
    }

    @Test
    public void alwaysFailsWithException() {
        throw new UnsupportedOperationException("This test always fails with an exception");
    }
}
