package org.example.repository;

import org.example.model.database.Debts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebtsRepository extends JpaRepository<Debts, Long> {

    List<Debts> findByIdDebtor(Integer idDebtor);
}
