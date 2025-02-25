package ru.otus.homework.exception;

public class SumToWithdrawShouldBePositiveException extends RuntimeException {
    public SumToWithdrawShouldBePositiveException(int sum) {
        super(String.format("Sum to withdraw should be positive: %d", sum));
    }
}
