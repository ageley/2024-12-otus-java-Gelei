package ru.otus.homework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework.exception.BanknotesCapacityExceededException;
import ru.otus.homework.exception.RemainingSumCantBeWithdrawnException;
import ru.otus.homework.exception.SumToWithdrawShouldBePositiveException;
import ru.otus.homework.exception.UnsupportedBanknotesFoundException;

@Slf4j
public class StandardAtm implements Atm {
    private final Vault vault;

    /**
     * @param vault A vault to store cassettes.
     */
    public StandardAtm(Vault vault) {
        this.vault = vault;
    }

    @Override
    public void addCassette(Cassette cassette) {
        vault.addCassette(cassette);
        log.info("Cassette added: {}", cassette);
    }

    @Override
    public Cassette removeCassette(int index) {
        Cassette cassette = vault.removeCassette(index);
        log.info("Cassette removed: {}", cassette);
        return cassette;
    }

    private void checkBanknotesSupported(List<Integer> banknotes) {
        Set<Integer> supportedBanknotes =
                vault.getCassettes().stream().map(Cassette::getBanknote).collect(Collectors.toSet());

        Map<Integer, Integer> unsupportedBanknotes = banknotes.stream()
                .filter(banknote -> !supportedBanknotes.contains(banknote))
                .collect(Collectors.toMap(banknote -> banknote, banknote -> 1, Integer::sum));

        if (!unsupportedBanknotes.isEmpty()) {
            throw new UnsupportedBanknotesFoundException(supportedBanknotes, unsupportedBanknotes);
        }
    }

    private void checkBanknotesCapacity(Map<Integer, Integer> banknotesAmount) {
        Map<Integer, Integer> capacity = vault.getCassettes().stream()
                .collect(Collectors.toMap(Cassette::getBanknote, Cassette::calculateCapacity, Integer::sum));

        for (Entry<Integer, Integer> banknoteAmount : banknotesAmount.entrySet()) {
            capacity.merge(
                    banknoteAmount.getKey(),
                    banknoteAmount.getValue(),
                    (banknoteCapacity, amount) -> banknoteCapacity - amount);
        }

        Map<Integer, Integer> exceededCapacities = capacity.entrySet().stream()
                .filter(capacityEntry -> capacityEntry.getValue() < 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        if (!exceededCapacities.isEmpty()) {
            throw new BanknotesCapacityExceededException(exceededCapacities);
        }
    }

    /**
     * @param banknotes A list of banknotes to deposit into the ATM.
     */
    @Override
    public void deposit(List<Integer> banknotes) {
        checkBanknotesSupported(banknotes);

        Map<Integer, Integer> banknotesAmount =
                banknotes.stream().collect(Collectors.toMap(banknote -> banknote, banknote -> 1, Integer::sum));

        checkBanknotesCapacity(banknotesAmount);

        log.info("Banknotes to be deposited: {}", banknotesAmount);

        for (Entry<Integer, Integer> banknoteAmount : banknotesAmount.entrySet()) {
            for (Cassette cassette : vault.getCassettes()) {
                if (cassette.calculateCapacity() == 0
                        || banknoteAmount.getValue() == 0
                        || !banknoteAmount.getKey().equals(cassette.getBanknote())) {
                    continue;
                }

                banknoteAmount.setValue(cassette.add(banknoteAmount.getValue()));
            }
        }

        log.info("Deposit complete");
    }

    private static void checkSumIsPositive(int sum) {
        if (sum <= 0) {
            throw new SumToWithdrawShouldBePositiveException(sum);
        }
    }

    private static void checkSumCouldBeWithdrawn(int sum) {
        if (sum != 0) {
            throw new RemainingSumCantBeWithdrawnException(sum);
        }
    }

    /**
     * @param sum A sum to withdraw from the ATM.
     */
    @Override
    public void withdraw(int sum) {
        checkSumIsPositive(sum);

        Map<Integer, Integer> banknotesAmount = new HashMap<>();

        for (Cassette cassette : vault.getCassettes()) {
            if (sum == 0) {
                break;
            }

            int banknotesToWithdraw = Math.min(sum / cassette.getBanknote(), cassette.getAmount());

            if (banknotesToWithdraw > 0) {
                banknotesAmount.merge(cassette.getBanknote(), banknotesToWithdraw, Integer::sum);
                sum -= banknotesToWithdraw * cassette.getBanknote();
            }
        }

        checkSumCouldBeWithdrawn(sum);

        log.info("Banknotes to be withdrawn: {}", banknotesAmount);

        for (Entry<Integer, Integer> banknoteAmount : banknotesAmount.entrySet()) {
            for (Cassette cassette : vault.getCassettes()) {
                if (cassette.getAmount() == 0
                        || banknoteAmount.getValue() == 0
                        || !banknoteAmount.getKey().equals(cassette.getBanknote())) {
                    continue;
                }

                banknoteAmount.setValue(cassette.retrieve(banknoteAmount.getValue()));
            }
        }

        log.info("Withdraw complete");
    }

    /**
     * @return Total remaining money in the ATM.
     */
    @Override
    public int calculateRemainingSum() {
        int sum = vault.getCassettes().stream()
                .mapToInt(cassette -> cassette.getAmount() * cassette.getBanknote())
                .sum();

        log.info("Total remaining money: {}", sum);
        return sum;
    }
}
