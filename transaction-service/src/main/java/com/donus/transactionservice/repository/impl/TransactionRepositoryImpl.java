package com.donus.transactionservice.repository.impl;

import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.domain.enums.TransactionType;
import com.donus.transactionservice.repository.TransactionRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    @Autowired
    public MongoTemplate mongoTemplate;

    @Override
    public List<Transaction> findByAccountAndDateAndType(Long accountId,
                                                  Date dateInitial,
                                                  Date dateFinal,
                                                  TransactionType type) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("sourceAccountId").is(accountId),
                Criteria.where("destinationAccountId").is(accountId)
        ));

        if(dateInitial != null && dateFinal == null)
            query.addCriteria(Criteria.where("createdDate").gte(dateInitial));
        else if(dateFinal != null && dateInitial == null)
            query.addCriteria(Criteria.where("createdDate").lte(dateFinal));
        else if(dateInitial != null && dateFinal != null)
            query.addCriteria(Criteria.where("createdDate").gte(dateInitial).lte(dateFinal));

        if(type != null)
            query.addCriteria(Criteria.where("type").is(type));

        return mongoTemplate.find(query, Transaction.class);
    }
}
