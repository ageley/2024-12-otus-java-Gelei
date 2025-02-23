package ru.otus.homework;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Application {
    public static void main(String[] args) {
        new Demo().action();
    }
}
