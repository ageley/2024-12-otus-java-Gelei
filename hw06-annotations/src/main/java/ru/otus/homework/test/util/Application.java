package ru.otus.homework.test.util;

import static ru.otus.homework.test.util.TestRunner.runTests;

import java.lang.reflect.InvocationTargetException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Application {
    public static void main(String[] args)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException,
                    IllegalAccessException {
        runTests(args[0]);
    }
}
