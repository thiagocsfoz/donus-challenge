package com.donus.transactionservice.service;

import com.donus.transactionservice.domain.entities.Balance;
import com.donus.transactionservice.repository.BalanceRepository;
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
public class BalanceServiceTests {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    public void when_get_balance_then_return_balance()
    {
        Balance balance = new Balance(123L);
        balance.setValue(1000D);

        when(balanceRepository.findByAccountId(123L)).thenReturn(Optional.of(balance));

        Balance balanceDb = this.balanceService.getBalanceByAccount(123L);

        Assertions.assertNotNull(balanceDb);
        Assertions.assertEquals(123L, balanceDb.getAccountId());
        Assertions.assertEquals(1000L, balanceDb.getValue());
    }
    
    @Test
    public void when_get_balance_not_found_return_new_balance() {
        when(balanceRepository.save(any(Balance.class))).thenReturn(new Balance(123L));

        Balance balanceDb = this.balanceService.getBalanceByAccount(123L);

        Assertions.assertNotNull(balanceDb);
        Assertions.assertEquals(123L, balanceDb.getAccountId());
        Assertions.assertEquals(0D, balanceDb.getValue());
    }
}
