package com.donus.transactionservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TransferDto {

    private Long sourceAccountId;

    private Long destinationAccountId;

    @NotNull
    private Double value;

}
