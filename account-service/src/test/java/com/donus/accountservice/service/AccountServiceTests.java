package com.donus.accountservice.service;

import com.donus.accountservice.domain.entities.Account;
import com.donus.accountservice.domain.entities.Agency;
import com.donus.accountservice.domain.entities.Client;
import com.donus.accountservice.repository.AccountRepository;
import com.donus.accountservice.repository.AgencyRepository;
import com.donus.accountservice.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    AccountRepository accountRepository;

    @Mock
    AgencyRepository agencyRepository;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    public void when_create_account_it_should_return_account() {
        Client client = new Client();
        client.setName("Cliente Teste");
        client.setCpf("08111041098");

        Agency agency = new Agency();
        agency.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setAgency(agency);
        account.setClient(client);

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(agencyRepository.findById(agency.getId())).thenReturn(Optional.of(agency));

        Account accountCreated = accountService.createAccount(client, agency.getId());

        Assertions.assertNotNull(accountCreated);
        Assertions.assertNotNull(accountCreated.getId());

        Assertions.assertNotNull(accountCreated.getAgency());
        Assertions.assertNotNull(accountCreated.getAgency().getId());
        assertEquals(1L, accountCreated.getAgency().getId());
    }

    @Test
    public void when_create_account_whithout_agency_it_should_return_notfound()
    {
        Client client = new Client();
        client.setName("Cliente Teste");
        client.setCpf("08111041098");

        Agency agency = new Agency();
        agency.setId(2L);

        Account account = new Account();
        account.setId(1L);
        account.setAgency(agency);
        account.setClient(client);

        when(agencyRepository.findById(agency.getId())).thenThrow(ResponseStatusException.class);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            Account accountCreated = accountService.createAccount(client, agency.getId());
        });
    }
}
