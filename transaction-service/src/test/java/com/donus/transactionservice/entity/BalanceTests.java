package com.donus.transactionservice.entity;

import com.donus.transactionservice.domain.entities.Balance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BalanceTests {

    @Test
    public void when_withdraw_then_return_withdraw_value() {
        Balance balance = new Balance();
        balance.setValue(100D);
        balance.withdraw(50D, 0.01D);

        Assertions.assertEquals(49.5D, balance.getValue());
    }

    @Test
    public void when_deposit_then_return_deposit_value() {
        Balance balance = new Balance();
        balance.setValue(0D);
        balance.deposit(10D, 0.005);

        Assertions.assertEquals(10.05D, balance.getValue());
    }

    @Test
    public void when_withdraw_without_then_return_withdraw_value() {
        Balance balance = new Balance();
        balance.setValue(100D);
        balance.withdraw(50D, 0D);

        Assertions.assertEquals(50D, balance.getValue());
    }

    @Test
    public void when_deposit_without_then_return_deposit_value() {
        Balance balance = new Balance();
        balance.setValue(0D);
        balance.deposit(50D, 0D);

        Assertions.assertEquals(50D, balance.getValue());
    }
}
