package ru.otus.homework;

import java.util.List;

public interface Atm {
    void addCassette(Cassette cassette);

    Cassette removeCassette(int index);

    void deposit(List<Integer> banknotes);

    void withdraw(int sum);

    int calculateRemainingSum();
}
