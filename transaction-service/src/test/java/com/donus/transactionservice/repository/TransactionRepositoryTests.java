package com.donus.transactionservice.repository;

import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.domain.enums.TransactionStatus;
import com.donus.transactionservice.domain.enums.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@DataMongoTest
@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setup() throws ParseException {
        transactionRepository.deleteAll();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        Transaction transaction1 = new Transaction();
        transaction1.setDestinationAccountId(123L);
        transaction1.setValue(100D);
        transaction1.setCreatedDate(format.parse("14/01/2021 14:20:00"));
        transaction1.setType(TransactionType.DEPOSIT);
        transaction1.setStatus(TransactionStatus.SUCCESS);

        Transaction transaction2 = new Transaction();
        transaction2.setSourceAccountId(123L);
        transaction2.setValue(100D);
        transaction2.setCreatedDate(format.parse("13/01/2021 14:20:00"));
        transaction2.setType(TransactionType.WITHDRAW);
        transaction2.setStatus(TransactionStatus.SUCCESS);

        Transaction transaction3 = new Transaction();
        transaction3.setSourceAccountId(123L);
        transaction3.setDestinationAccountId(124L);
        transaction3.setValue(100D);
        transaction3.setCreatedDate(format.parse("15/01/2021 14:20:00"));
        transaction3.setType(TransactionType.TRANSFER);
        transaction3.setStatus(TransactionStatus.SUCCESS);

        Transaction transaction4 = new Transaction();
        transaction4.setSourceAccountId(124L);
        transaction4.setDestinationAccountId(123L);
        transaction4.setValue(100D);
        transaction4.setCreatedDate(format.parse("16/01/2021 14:20:00"));
        transaction4.setType(TransactionType.TRANSFER);
        transaction4.setStatus(TransactionStatus.SUCCESS);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);
        transactionRepository.save(transaction4);
    }

    @Test
    public void when_find_transaction_by_accountid_then_return_transactions() {
        List<Transaction> transactions = this.transactionRepository.findByAccountAndDateAndType(
                123L,
                null,
                null,
                null
        );

        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(4, transactions.size());
    }

    @Test
    public void when_find_transaction_by_date_then_return_transactions() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        List<Transaction> transactions = this.transactionRepository.findByAccountAndDateAndType(
                123L,
                format.parse("15/01/2021 10:20:00"),
                format.parse("16/01/2021 14:20:00"),
                null
        );

        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(2, transactions.size());
    }

    @Test
    public void when_find_transaction_by_type_then_return_transactions() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        List<Transaction> transactions = this.transactionRepository.findByAccountAndDateAndType(
                123L,
                null,
                null,
                TransactionType.TRANSFER
        );

        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(2, transactions.size());
    }
}
