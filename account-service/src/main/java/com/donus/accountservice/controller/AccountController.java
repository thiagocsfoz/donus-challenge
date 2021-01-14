package com.donus.accountservice.controller;

import com.donus.accountservice.domain.dto.ClientDto;
import com.donus.accountservice.domain.entities.Account;
import com.donus.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Operation(summary = "Criar uma nova conta para o cliente informado.", tags = {"account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agência não encontrada") })
    @ResponseBody
    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok().body(accountService.createAccount(clientDto.toClient(), clientDto.getAgencyId()));
    }

}
