package org.example.repository;

import org.example.model.database.Debtor;
import org.example.model.database.Debts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtsRepository extends JpaRepository<Debts, Long> {
}
