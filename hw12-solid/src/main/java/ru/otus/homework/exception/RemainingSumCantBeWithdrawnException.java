package ru.otus.homework.exception;

public class RemainingSumCantBeWithdrawnException extends RuntimeException {
    public RemainingSumCantBeWithdrawnException(int sum) {
        super(String.format("Remaining sum can't be withdrawn: %d", sum));
    }
}
