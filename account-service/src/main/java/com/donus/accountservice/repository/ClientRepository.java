package com.donus.accountservice.repository;

import com.donus.accountservice.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {}
