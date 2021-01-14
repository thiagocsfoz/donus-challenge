package com.donus.transactionservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WithdrawDto {

    private Long accountId;

    private Double value;
}
