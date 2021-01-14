package com.donus.transactionservice.service;

import com.donus.transactionservice.domain.entities.Balance;
import com.donus.transactionservice.domain.entities.Setting;
import com.donus.transactionservice.domain.entities.Transaction;
import com.donus.transactionservice.domain.enums.TransactionStatus;
import com.donus.transactionservice.domain.enums.TransactionType;
import com.donus.transactionservice.repository.SettingRepository;
import com.donus.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction deposit(Long accountId, Double value) {
        Setting setting = this.settingRepository.findById("1")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuração de taxa de deposito não encontrada."));

        Balance balance = this.balanceService.getBalanceByAccount(accountId);
        balance.deposit(value, setting.getDepositTax());
        balanceService.updateBalance(balance);

        Transaction transaction = new Transaction();
        transaction.setDestinationAccountId(accountId);
        transaction.setValue(value);
        transaction.setTax(setting.getDepositTax());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.SUCCESS);

        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(Long accountId, Double value) {
        Setting setting = this.settingRepository.findById("1")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuração de taxa de retirada não encontrada."));

        Balance balance = this.balanceService.getBalanceByAccount(accountId);

        if(balance.withdraw(value, setting.getWithdrawTax())) {
            balanceService.updateBalance(balance);

            Transaction transaction = new Transaction();
            transaction.setSourceAccountId(accountId);
            transaction.setValue(value);
            transaction.setTax(setting.getWithdrawTax());
            transaction.setType(TransactionType.WITHDRAW);
            transaction.setStatus(TransactionStatus.SUCCESS);

            return transactionRepository.save(transaction);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente em conta.");
        }
    }

    public Transaction transfer(Long sourceAccountId, Long destinationAccountId, Double value) {
        Setting setting = this.settingRepository.findById("1")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuração de taxa de transferência não encontrada."));

        Balance sourceBalance = this.balanceService.getBalanceByAccount(sourceAccountId);
        Balance destinationBalance = this.balanceService.getBalanceByAccount(destinationAccountId);

        if(sourceBalance.withdraw(value, setting.getTransferTax())) {
            balanceService.updateBalance(sourceBalance);

            destinationBalance.deposit(value, 0D);
            balanceService.updateBalance(destinationBalance);

            Transaction transaction = new Transaction();
            transaction.setSourceAccountId(sourceAccountId);
            transaction.setDestinationAccountId(destinationAccountId);
            transaction.setValue(value);
            transaction.setTax(setting.getTransferTax());
            transaction.setType(TransactionType.TRANSFER);
            transaction.setStatus(TransactionStatus.SUCCESS);

            return transactionRepository.save(transaction);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente em conta.");
        }
    }

    public List<Transaction> history(Long accountId, Date dateInitial, Date dateFinal, TransactionType type) {
        return transactionRepository.findByAccountAndDateAndType(
                accountId,
                dateInitial,
                dateFinal,
                type
        );
    }
}
