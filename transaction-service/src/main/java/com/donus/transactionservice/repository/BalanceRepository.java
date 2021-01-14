package com.donus.transactionservice.repository;

import com.donus.transactionservice.domain.entities.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends MongoRepository<Balance, String> {
    Optional<Balance> findByAccountId(Long accountId);
}
