package com.donus.transactionservice.domain.entities;

import com.donus.transactionservice.domain.enums.TransactionStatus;
import com.donus.transactionservice.domain.enums.TransactionType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private Date createdDate = new Date();

    private Long sourceAccountId;

    private Long destinationAccountId;

    private Double value;

    private Double tax;

    private TransactionType type;

    private TransactionStatus status;

    private String messageFail;
}
