package org.example.repository;

import org.example.model.database.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtorRepository extends JpaRepository<Debtor, Long> {

    Debtor findByDocNumber(String docNumber);
    Debtor findByName(String docNumber);
    Debtor findByEmail(String docNumber);

    List<Debtor> findByNameContainingIgnoreCase(String name);
    List<Debtor> findByEmailContainingIgnoreCase(String email);
    List<Debtor> findByDocNumberContaining(String docNumber);
}
