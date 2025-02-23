package ru.otus.homework;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestLogging implements TestLoggingInterface {
    @Log
    @Override
    public void calculation(int param1) {
        log.info("calculation method with 1 param executed");
    }

    @Override
    public void calculation(int param1, int param2) {
        log.info("calculation method with 2 params executed");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        log.info("calculation method with 3 params executed");
    }
}
