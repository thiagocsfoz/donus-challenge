package com.donus.transactionservice.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "settings")
public class Setting {

    @Id
    private String id;

    private Double withdrawTax;

    private Double depositTax;

    private Double transferTax;
}
