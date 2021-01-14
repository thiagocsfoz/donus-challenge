package com.donus.accountservice.domain.dto;

import com.donus.accountservice.domain.entities.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {

    @NotBlank(message = "Campo nome obrigatório")
    private String name;

    @NotBlank(message = "Campo CPF obrigatório")
    private String cpf;

    @NotNull(message = "Campo agência obrigatório")
    private Long AgencyId;

    public Client toClient() {
        return new Client(this.name, this.cpf);
    }
}
