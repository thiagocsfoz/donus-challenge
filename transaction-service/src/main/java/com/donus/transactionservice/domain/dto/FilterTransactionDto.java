package com.donus.transactionservice.domain.dto;

import com.donus.transactionservice.domain.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FilterTransactionDto {

    Long accountId;

    Date dateInitial;

    Date dateFinal;

    TransactionType type;
}
