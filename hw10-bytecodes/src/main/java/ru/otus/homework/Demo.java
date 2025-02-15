package ru.otus.homework;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Demo {
    private final TestLoggingInterface testLogging;
    private final TestLoggingInterface testLoggingProxied;

    public Demo() {
        this.testLogging = TestLoggingFactory.createTestLogging();
        this.testLoggingProxied = TestLoggingFactory.createTestLoggingProxied();
    }

    public void action() {
        log.info("no proxy");
        testLogging.calculation(1);
        testLogging.calculation(1, 2);
        testLogging.calculation(1, 2, "three");
        log.info("proxy");
        testLoggingProxied.calculation(1);
        testLoggingProxied.calculation(1, 2);
        testLoggingProxied.calculation(1, 2, "three");
    }
}
