package com.donus.accountservice.seeders;

import com.donus.accountservice.domain.entities.Agency;
import com.donus.accountservice.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AgencyDataLoader implements CommandLineRunner {

    @Autowired
    AgencyRepository agencyRepository;

    @Override
    public void run(String... args) throws Exception {
        if(agencyRepository.count() == 0){
            Agency agency = new Agency();
            agency.setId(1L);
            agency.setName("Brasil");

            agencyRepository.save(agency);
        }
    }
}
