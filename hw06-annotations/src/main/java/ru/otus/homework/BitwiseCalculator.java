package ru.otus.homework;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BitwiseCalculator {
    public static int not(int input) {
        return ~input;
    }

    public static int and(int left, int right) {
        return left & right;
    }

    public static int or(int left, int right) {
        return left | right;
    }

    public static int xor(int left, int right) {
        return left ^ right;
    }

    public static int shiftLeft(int value, int offset) {
        return value << offset;
    }

    public static int shiftRight(int value, int offset) {
        return value >> offset;
    }

    public static int shiftRightUnsigned(int value, int offset) {
        return value >>> offset;
    }
}
