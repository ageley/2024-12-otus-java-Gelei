package ru.otus.homework.exception;

public class CassettesCapacityExceededException extends RuntimeException {
    public CassettesCapacityExceededException(int maxCapacity) {
        super(String.format("A maximum capacity of %d cassettes reached", maxCapacity));
    }
}
