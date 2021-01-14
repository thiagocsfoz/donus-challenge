package com.donus.accountservice.repository;

import com.donus.accountservice.domain.entities.Account;
import com.donus.accountservice.domain.entities.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

@DataJpaTest
@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void when_insert_new_client_then_return_client()
    {
        Client client = new Client();
        client.setName("Cliente teste");
        client.setCpf("34765244008");

        clientRepository.saveAndFlush(client);

        Client clientDb = clientRepository.getOne(client.getId());

        Assertions.assertNotNull(clientDb);
    }

    @Test
    public void when_insert_client_exists_cpf_then_return_dataintegrityviolationexception()
    {
        Client client = new Client("Cliente teste", "34765244008");
        Client client2 = new Client("Cliente teste2", "34765244008");

        clientRepository.saveAndFlush(client);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            clientRepository.saveAndFlush(client2);
        });
    }

    @Test
    public void when_insert_client_invalid_cpf_then_return_constraintviolationexception()
    {
        Client client = new Client("Cliente teste", "34765244009");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            clientRepository.saveAndFlush(client);
        });
    }
}
