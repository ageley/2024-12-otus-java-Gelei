package ru.otus.homework;

import java.util.List;

public interface Vault {
    List<Cassette> getCassettes();

    void addCassette(Cassette cassette);

    Cassette removeCassette(int index);

    int getMaxCassettesCapacity();
}
