package com.donus.accountservice.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CPF(message = "CPF inválido")
    @Column(unique=true)
    private String cpf;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Client(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
