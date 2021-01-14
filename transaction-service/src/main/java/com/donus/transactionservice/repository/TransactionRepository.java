package com.donus.transactionservice.repository;

import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.domain.enums.TransactionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>, TransactionRepositoryCustom {}
