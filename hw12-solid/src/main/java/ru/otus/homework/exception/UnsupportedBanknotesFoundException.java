package ru.otus.homework.exception;

import java.util.Map;
import java.util.Set;

public class UnsupportedBanknotesFoundException extends RuntimeException {
    public UnsupportedBanknotesFoundException(
            Set<Integer> supportedBanknotes, Map<Integer, Integer> unsupportedBanknotes) {
        super(String.format(
                "Supported banknotes are: %s, but unsupported banknotes found: %s",
                supportedBanknotes.toString(), unsupportedBanknotes.toString()));
    }
}
