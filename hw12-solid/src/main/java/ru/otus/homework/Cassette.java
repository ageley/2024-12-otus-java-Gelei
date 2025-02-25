package ru.otus.homework;

public interface Cassette {
    int add(int amount);

    int retrieve(int amount);

    int calculateCapacity();

    int getBanknote();

    int getMaxBanknotesCapacity();

    int getAmount();
}
