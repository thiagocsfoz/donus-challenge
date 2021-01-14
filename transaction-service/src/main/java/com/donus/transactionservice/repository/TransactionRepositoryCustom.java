package com.donus.transactionservice.repository;

import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.domain.enums.TransactionType;

import java.util.Date;
import java.util.List;

public interface TransactionRepositoryCustom {
    public List<Transaction> findByAccountAndDateAndType(Long accountId,
                                                         Date dateInitial,
                                                         Date dateFinal,
                                                         TransactionType type);
}
