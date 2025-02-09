package ru.otus.homework.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestRunnerTest {
    @Test
    void allTestArePassed() {
        TestRunner testRunner = Mockito.mock(TestRunner.class);
        Mockito.when(testRunner.runTests(Mockito.any())).thenCallRealMethod();
        Mockito.when(testRunner.runTest(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(true);
        TestResult testResult = testRunner.runTests("ru.otus.homework.test.BitwiseCalculatorTest");
        assertEquals(2, testResult.total());
        assertEquals(2, testResult.pass());
        assertEquals(0, testResult.fail());
    }

    @Test
    void allTestAreFailed() {
        TestRunner testRunner = Mockito.mock(TestRunner.class);
        Mockito.when(testRunner.runTests(Mockito.any())).thenCallRealMethod();
        Mockito.when(testRunner.runTest(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(false);
        TestResult testResult = testRunner.runTests("ru.otus.homework.test.BitwiseCalculatorTest");
        assertEquals(2, testResult.total());
        assertEquals(0, testResult.pass());
        assertEquals(2, testResult.fail());
    }
}
