package ru.otus.homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import ru.otus.homework.exception.CassettesCapacityExceededException;

public class CassetteOrderingVault implements Vault {
    @Getter
    public final int maxCassettesCapacity;

    public final List<Cassette> cassettes;

    /**
     * @param maxCassettesCapacity A maximum cassettes' capacity.
     */
    public CassetteOrderingVault(int maxCassettesCapacity) {
        this.maxCassettesCapacity = maxCassettesCapacity;
        this.cassettes = new ArrayList<>();
    }

    /**
     * @return An unmodifiable list of cassettes loaded into the vault.
     */
    @Override
    public List<Cassette> getCassettes() {
        return Collections.unmodifiableList(cassettes);
    }

    private void checkCassettesCapacity() {
        int capacity = maxCassettesCapacity - cassettes.size();

        if (capacity < 1) {
            throw new CassettesCapacityExceededException(maxCassettesCapacity);
        }
    }

    /**
     * @param cassette A cassette to add into the vault.
     *                 After a cassette is added, all cassettes are ordered by denomination in reverse order.
     */
    @Override
    public void addCassette(Cassette cassette) {
        checkCassettesCapacity();
        cassettes.add(cassette);
        cassettes.sort(Comparator.comparingInt(Cassette::getBanknote).reversed());
    }

    /**
     * @param index An index of a cassette to be removed from the vault.
     * @return A removed cassette.
     */
    @Override
    public Cassette removeCassette(int index) {
        return cassettes.remove(index);
    }
}
