package ru.otus.homework.exception;

import java.util.Map;

public class BanknotesCapacityExceededException extends RuntimeException {
    public BanknotesCapacityExceededException(Map<Integer, Integer> exceededCapacities) {
        super(String.format("Banknotes that can't be deposited: %s", exceededCapacities.toString()));
    }
}
