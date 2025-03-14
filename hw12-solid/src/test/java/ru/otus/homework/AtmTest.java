package ru.otus.homework;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.exception.BanknotesCapacityExceededException;
import ru.otus.homework.exception.CassettesCapacityExceededException;
import ru.otus.homework.exception.RemainingSumCantBeWithdrawnException;
import ru.otus.homework.exception.SumToWithdrawShouldBePositiveException;
import ru.otus.homework.exception.UnsupportedBanknotesFoundException;

@Slf4j
class AtmTest {
    private Atm atm;

    @BeforeEach
    void setUp() {
        Cassette cassette500 = new StandardCassette(500, 5);
        cassette500.add(3);
        Cassette cassette1000 = new StandardCassette(1000, 5);
        cassette1000.add(2);
        Cassette cassette5000 = new StandardCassette(5000, 1);
        cassette5000.add(1);
        Vault vault = new CassetteOrderingVault(4);
        vault.addCassette(cassette500);
        vault.addCassette(cassette1000);
        vault.addCassette(cassette5000);
        atm = new StandardAtm(vault);
    }

    @Test
    void moneyCouldBeDeposited() {
        int sumBeforeDeposit = atm.calculateRemainingSum();
        List<Integer> banknotes = Arrays.asList(500, 1000, 500);
        int sumToDeposit = banknotes.stream().mapToInt(banknote -> banknote).sum();
        atm.deposit(banknotes);
        assertEquals(sumBeforeDeposit + sumToDeposit, atm.calculateRemainingSum());
    }

    @Test
    void moneyCouldBeWithdrawn() {
        int sumBeforeWithdraw = atm.calculateRemainingSum();
        int sumToWithdraw = 8500;
        atm.withdraw(sumToWithdraw);
        assertEquals(sumBeforeWithdraw - sumToWithdraw, atm.calculateRemainingSum());
    }

    @Test
    void banknotesCapacityExceeded() {
        int sumBeforeDeposit = atm.calculateRemainingSum();
        List<Integer> banknotes = Arrays.asList(500, 1000, 500, 5000, 5000);
        Exception e = assertThrows(BanknotesCapacityExceededException.class, () -> atm.deposit(banknotes));
        log.info(e.getMessage());
        assertEquals(sumBeforeDeposit, atm.calculateRemainingSum());
    }

    @Test
    void unsupportedBanknotesFound() {
        int sumBeforeDeposit = atm.calculateRemainingSum();
        List<Integer> banknotes = Arrays.asList(500, 1000, 100);
        Exception e = assertThrows(UnsupportedBanknotesFoundException.class, () -> atm.deposit(banknotes));
        log.info(e.getMessage());
        assertEquals(sumBeforeDeposit, atm.calculateRemainingSum());
    }

    @Test
    void remainingSumCantBeWithdrawn() {
        int sumBeforeWithdraw = atm.calculateRemainingSum();
        int sumToWithdraw = 9500;
        Exception e = assertThrows(RemainingSumCantBeWithdrawnException.class, () -> atm.withdraw(sumToWithdraw));
        log.info(e.getMessage());
        assertEquals(sumBeforeWithdraw, atm.calculateRemainingSum());
    }

    @Test
    void sumToWithdrawShouldBePositive() {
        int sumBeforeWithdraw = atm.calculateRemainingSum();
        int sumToWithdraw = 0;
        Exception e = assertThrows(SumToWithdrawShouldBePositiveException.class, () -> atm.withdraw(sumToWithdraw));
        log.info(e.getMessage());
        assertEquals(sumBeforeWithdraw, atm.calculateRemainingSum());
    }

    @Test
    void cassetteCouldBeAdded() {
        int sumBeforeCassetteAdded = atm.calculateRemainingSum();
        Cassette cassette5000 = new StandardCassette(5000, 1);
        cassette5000.add(1);
        atm.addCassette(cassette5000);
        assertEquals(
                sumBeforeCassetteAdded + cassette5000.getAmount() * cassette5000.getBanknote(),
                atm.calculateRemainingSum());
    }

    @Test
    void cassetteCouldBeRemoved() {
        int sumBeforeCassetteRemoved = atm.calculateRemainingSum();
        Cassette cassette = atm.removeCassette(0);
        assertEquals(
                sumBeforeCassetteRemoved - cassette.getAmount() * cassette.getBanknote(), atm.calculateRemainingSum());
    }

    @Test
    void cassettesCapacityExceeded() {
        int sumBeforeCassetteAdded = atm.calculateRemainingSum();
        Cassette cassette5000 = new StandardCassette(5000, 1);
        cassette5000.add(1);
        atm.addCassette(cassette5000);
        Exception e = assertThrows(CassettesCapacityExceededException.class, () -> atm.addCassette(cassette5000));
        log.info(e.getMessage());
        assertEquals(
                sumBeforeCassetteAdded + cassette5000.getAmount() * cassette5000.getBanknote(),
                atm.calculateRemainingSum());
    }

    @Test
    void cassetteCouldNotBeRemoved() {
        int sumBeforeCassetteRemoved = atm.calculateRemainingSum();
        Exception e = assertThrows(RuntimeException.class, () -> atm.removeCassette(4));
        log.info(e.getMessage());
        assertEquals(sumBeforeCassetteRemoved, atm.calculateRemainingSum());
    }
}
