package com.donus.accountservice.repository;

import com.donus.accountservice.domain.entities.Account;
import com.donus.accountservice.domain.entities.Agency;
import com.donus.accountservice.domain.entities.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTests {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AgencyRepository agencyRepository;

    @Test
    public void when_insert_new_account_then_return_account()
    {
        Client client = new Client("Cliente Teste", "34765244008");
        client = clientRepository.save(client);

        Agency agency = new Agency();
        agency.setName("Agência teste");
        agency = agencyRepository.save(agency);

        Account account = new Account();
        account.setClient(client);
        account.setAgency(agency);

        accountRepository.save(account);

        Account accountDb = accountRepository.getOne(account.getId());

        Assertions.assertNotNull(accountDb);
    }
}
