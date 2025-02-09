package ru.otus.homework.test.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Application {
    public static void main(String[] args) {
        new TestRunner().runTests(args[0]);
    }
}
