package org.example.repository;

import org.example.model.database.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DebtorRepository extends JpaRepository<Debtor, Long> {

    Debtor findByDocNumber(String docNumber);
}
