package com.donus.accountservice.service;

import com.donus.accountservice.domain.entities.Account;
import com.donus.accountservice.domain.entities.Agency;
import com.donus.accountservice.domain.entities.Client;
import com.donus.accountservice.repository.AccountRepository;
import com.donus.accountservice.repository.AgencyRepository;
import com.donus.accountservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Service
@Transactional
public class AccountService {

    @Autowired
    AgencyRepository agencyRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(Client client, Long agencyId) {
        try {
            clientRepository.saveAndFlush(client);
        } catch ( DataIntegrityViolationException dataIntegrityViolationException ) {
            throw new DataIntegrityViolationException("CPF já cadastrado");
        } catch ( ConstraintViolationException constraintViolationException ) {
            throw new ConstraintViolationException("CPF inválido", constraintViolationException.getConstraintViolations());
        }

        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agency Not Found"));

        Account account = new Account();
        account.setClient(client);
        account.setAgency(agency);

        return accountRepository.save(account);
    }
}
