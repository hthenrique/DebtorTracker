package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.debts.CreateDebt;
import org.example.model.debts.UpdateDebt;
import org.example.service.impl.DebtsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/debts")
public class ControllerDebts {

    @Autowired
    private DebtsServiceImpl debtsService;

    @GetMapping("/fetch_all_debts")
    public ResponseEntity<ApplicationResponse> fetchDebts() throws RepositoryException, BusinessException {
        logSeparator();
        log.info(String.format("Fetching debts"));
        return ResponseEntity.ok(debtsService.fetchDebts());
    }

    @GetMapping("/fetch_debts")
    public ResponseEntity<ApplicationResponse> fetchDebtsByUser(@RequestHeader("id") String id) throws RepositoryException, BusinessException {
        logSeparator();
        log.info(String.format("Fetching Debts with Debtor id %s", id));
        return ResponseEntity.ok(debtsService.fetchDebtsByDebtor(id));
    }

    @PostMapping("/create_debt")
    public ResponseEntity<ApplicationResponse> create(@RequestBody CreateDebt debt) throws BusinessException, RepositoryException {
        logSeparator();
        return ResponseEntity.status(HttpStatus.CREATED).body(debtsService.create(debt));
    }

    @PatchMapping("/update_debt")
    public ResponseEntity<ApplicationResponse> update(@RequestBody UpdateDebt debt) throws BusinessException, RepositoryException, SystemException {
        logSeparator();
        return ResponseEntity.ok(debtsService.update(debt));
    }

    @DeleteMapping("/delete_debt")
    public ResponseEntity<ApplicationResponse> delete(@RequestHeader("id") String id) throws BusinessException, RepositoryException {
        logSeparator();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(debtsService.delete(id));
    }

    private void logSeparator() {
        log.info("====================================================================================================");
    }

}
