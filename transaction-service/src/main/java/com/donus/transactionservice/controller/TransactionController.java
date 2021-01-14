package com.donus.transactionservice.controller;

import com.donus.transactionservice.domain.dto.DepositDto;
import com.donus.transactionservice.domain.dto.FilterTransactionDto;
import com.donus.transactionservice.domain.dto.TransferDto;
import com.donus.transactionservice.domain.dto.WithdrawDto;
import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Operation(summary = "Realiza um deposito para a conta informada no valor informado.", tags = {"transaction"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposito realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Saldo insuficiente na conta."),
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada.") })
    @ResponseBody
    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody DepositDto depositDto) {
        return ResponseEntity.ok().body(this.transactionService.deposit(depositDto.getAccountId(), depositDto.getValue()));
    }

    @Operation(summary = "Realiza uma retirada para a conta informada no valor informado.", tags = {"transaction"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retirada realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Saldo insuficiente na conta."),
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada.") })
    @ResponseBody
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid @RequestBody WithdrawDto withdrawDto) {
        return ResponseEntity.ok().body(this.transactionService.withdraw(withdrawDto.getAccountId(), withdrawDto.getValue()));
    }

    @Operation(summary = "Realiza uma transferência da conta de uma conta para outra conta no valor informado.", tags = {"transaction"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Saldo insuficiente na conta."),
            @ApiResponse(responseCode = "404", description = "Configuração não encontrada.") })
    @ResponseBody
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@Valid @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok().body(this.transactionService.transfer(transferDto.getSourceAccountId(),
                transferDto.getDestinationAccountId(),
                transferDto.getValue()));
    }

    @Operation(summary = "Realiza consultar de transações de uma determinada conta.", tags = {"transaction"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizado com sucesso.") })
    @ResponseBody
    @PostMapping("/history")
    public ResponseEntity<List<Transaction>> history(@RequestBody FilterTransactionDto filterTransactionDto) {
        return ResponseEntity.ok().body(this.transactionService.history(filterTransactionDto.getAccountId(),
                filterTransactionDto.getDateInitial(),
                filterTransactionDto.getDateFinal(),
                filterTransactionDto.getType()));
    }
}
