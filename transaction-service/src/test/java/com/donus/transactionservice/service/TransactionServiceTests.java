package com.donus.transactionservice.service;

import com.donus.transactionservice.domain.entities.Balance;
import com.donus.transactionservice.domain.entities.Setting;
import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.domain.enums.TransactionStatus;
import com.donus.transactionservice.domain.enums.TransactionType;
import com.donus.transactionservice.repository.SettingRepository;
import com.donus.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    SettingRepository settingRepository;

    @Mock
    BalanceService balanceService;

    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void when_deposit_value_then_return_transaction() {
        Setting setting = new Setting();
        setting.setDepositTax(0.005D);

        Balance balance = new Balance(123L);
        balance.setValue(1000D);

        Transaction transaction = new Transaction();
        transaction.setDestinationAccountId(balance.getAccountId());
        transaction.setValue(100D);
        transaction.setTax(setting.getDepositTax());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.SUCCESS);

        when(settingRepository.findById("1")).thenReturn(Optional.of(setting));
        when(balanceService.getBalanceByAccount(balance.getAccountId())).thenReturn(balance);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction transationDb = this.transactionService.deposit(balance.getAccountId(), 100D);

        Assertions.assertNotNull(transationDb);
        Assertions.assertEquals(TransactionType.DEPOSIT, transationDb.getType());
        Assertions.assertEquals(balance.getAccountId(), transationDb.getDestinationAccountId());
        Assertions.assertEquals(100D, transationDb.getValue());
        Assertions.assertEquals(0.005D, transationDb.getTax());
        Assertions.assertEquals(TransactionStatus.SUCCESS, transationDb.getStatus());
    }

    @Test
    public void when_withdraw_value_then_return_transaction() {
        Setting setting = new Setting();
        setting.setWithdrawTax(0.01D);

        Balance balance = new Balance(123L);
        balance.setValue(101D);

        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(balance.getAccountId());
        transaction.setValue(100D);
        transaction.setTax(setting.getWithdrawTax());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.SUCCESS);

        when(settingRepository.findById("1")).thenReturn(Optional.of(setting));
        when(balanceService.getBalanceByAccount(balance.getAccountId())).thenReturn(balance);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction transationDb = this.transactionService.withdraw(balance.getAccountId(), 100D);

        Assertions.assertNotNull(transationDb);
        Assertions.assertEquals(TransactionType.WITHDRAW, transationDb.getType());
        Assertions.assertEquals(balance.getAccountId(), transationDb.getSourceAccountId());
        Assertions.assertEquals(100D, transationDb.getValue());
        Assertions.assertEquals(0.01D, transationDb.getTax());
        Assertions.assertEquals(TransactionStatus.SUCCESS, transationDb.getStatus());
    }

    @Test
    public void when_transfer_value_then_return_transaction() {
        Setting setting = new Setting();
        setting.setTransferTax(0D);

        Balance balanceSource = new Balance(123L);
        balanceSource.setValue(100D);

        Balance balanceDestination = new Balance(124L);
        balanceDestination.setValue(0D);

        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(balanceSource.getAccountId());
        transaction.setDestinationAccountId(balanceDestination.getAccountId());
        transaction.setValue(50D);
        transaction.setTax(setting.getTransferTax());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.SUCCESS);

        when(settingRepository.findById("1")).thenReturn(Optional.of(setting));
        when(balanceService.getBalanceByAccount(balanceSource.getAccountId())).thenReturn(balanceSource);
        when(balanceService.getBalanceByAccount(balanceDestination.getAccountId())).thenReturn(balanceDestination);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction transationDb = this.transactionService.transfer(balanceSource.getAccountId(), balanceDestination.getAccountId(), 50D);

        Assertions.assertNotNull(transationDb);
        Assertions.assertEquals(TransactionType.TRANSFER, transationDb.getType());
        Assertions.assertEquals(balanceSource.getAccountId(), transationDb.getSourceAccountId());
        Assertions.assertEquals(balanceDestination.getAccountId(), transationDb.getDestinationAccountId());
        Assertions.assertEquals(50D, transationDb.getValue());
        Assertions.assertEquals(0D, transationDb.getTax());
        Assertions.assertEquals(TransactionStatus.SUCCESS, transationDb.getStatus());
    }
}
