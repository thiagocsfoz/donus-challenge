package com.donus.transactionservice.service;

import com.donus.transactionservice.domain.entities.Balance;
import com.donus.transactionservice.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    public Balance getBalanceByAccount(Long accountId) {
        return this.balanceRepository.findByAccountId(accountId)
                .orElseGet(() -> balanceRepository.save(new Balance(accountId)));
    }

    public Balance updateBalance(Balance balance) {
        return this.balanceRepository.save(balance);
    }
}
