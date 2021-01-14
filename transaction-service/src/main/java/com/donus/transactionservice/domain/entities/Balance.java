package com.donus.transactionservice.domain.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "balances")
public class Balance {

    @Id
    private String id;

    Double value;

    Long accountId;

    public Balance(Long accountId) {
        this.accountId = accountId;
        this.value = 0D;
    }

    public void deposit(Double value, Double tax) {
        if(tax.equals(0D)) {
            this.value = this.value + value;
        } else {
            this.value = this.value + (value + (value * tax));
        }
    }

    public Boolean withdraw(Double value, Double withdrawTax) {
        double valueUpdated = this.value - (value + (value * withdrawTax));

        if( valueUpdated >= 0D  ) {
            this.value = valueUpdated;
            return true;
        } else {
            return false;
        }
    }
}
