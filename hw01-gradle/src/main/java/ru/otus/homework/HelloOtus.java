package ru.otus.homework;

import com.google.common.base.Strings;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {
        System.out.println(Strings.repeat(HelloOtus.class.getSimpleName(), 3));
    }
}
