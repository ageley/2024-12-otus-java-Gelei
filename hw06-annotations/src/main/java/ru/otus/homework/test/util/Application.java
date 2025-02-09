package ru.otus.homework.test.util;

import static ru.otus.homework.test.util.TestRunner.runTests;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Application {
    public static void main(String[] args) {
        runTests(args[0]);
    }
}
