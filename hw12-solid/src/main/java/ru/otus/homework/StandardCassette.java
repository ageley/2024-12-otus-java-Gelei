package ru.otus.homework;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StandardCassette implements Cassette {
    private final int banknote;
    private final int maxBanknotesCapacity;
    private int amount;

    /**
     * @param banknote A denomination of banknotes to store in a cassette.
     * @param maxBanknotesCapacity A maximum banknotes' capacity.
     */
    public StandardCassette(int banknote, int maxBanknotesCapacity) {
        this.banknote = banknote;
        this.maxBanknotesCapacity = maxBanknotesCapacity;
    }

    /**
     * @param amount An amount of banknotes to add to the cassette.
     * @return An amount of banknotes that can't be added.
     */
    @Override
    public int add(int amount) {
        int amountToAdd = Math.min(calculateCapacity(), amount);
        this.amount += amountToAdd;
        return amount - amountToAdd;
    }

    /**
     * @param amount An amount of banknotes to retrieve from the cassette.
     * @return An amount of banknotes that can't be retrieved.
     */
    @Override
    public int retrieve(int amount) {
        int amountToRetrieve = Math.min(this.amount, amount);
        this.amount -= amountToRetrieve;
        return amount - amountToRetrieve;
    }

    /**
     * @return A remaining banknotes' capacity.
     */
    @Override
    public int calculateCapacity() {
        return maxBanknotesCapacity - amount;
    }
}
