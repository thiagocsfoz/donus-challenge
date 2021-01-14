package com.donus.accountservice.repository;

import com.donus.accountservice.domain.entities.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency,Long> {}
