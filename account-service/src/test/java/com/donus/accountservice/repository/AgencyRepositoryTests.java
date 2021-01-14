package com.donus.accountservice.repository;

import com.donus.accountservice.domain.entities.Agency;
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
public class AgencyRepositoryTests {

    @Autowired
    AgencyRepository agencyRepository;

    @Test
    public void when_insert_new_agency_then_return_agency()
    {
        Agency agency = new Agency();
        agency.setName("Agência teste");

        agencyRepository.saveAndFlush(agency);

        Agency agencyDb = agencyRepository.getOne(agency.getId());

        Assertions.assertNotNull(agencyDb);
    }
}
