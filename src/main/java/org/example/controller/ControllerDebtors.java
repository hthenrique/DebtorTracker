package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.debtor.CreateDebtor;
import org.example.model.debtor.UpdateDebtor;
import org.example.service.DebtorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("api/debtors")
public class ControllerDebtors {

    @Autowired
    private DebtorsService debtorsService;

    @GetMapping("/fetch_debtors")
    public ResponseEntity<ApplicationResponse> fetchUsers() throws BusinessException, RepositoryException {
        logSeparator();
        log.info("Fetching Users");
        return ResponseEntity.ok(debtorsService.fetchUsers());
    }

    @GetMapping("/fetch_debtor")
    public ResponseEntity<ApplicationResponse> fetchUser(@RequestHeader("id") String id) throws RepositoryException, BusinessException {
        logSeparator();
        log.info(String.format("Fetching User with id %s", id));
        return ResponseEntity.ok(debtorsService.fetchUser(id));
    }

    @PostMapping("/create_debtor")
    public ResponseEntity<ApplicationResponse> create(@RequestBody CreateDebtor debtor) throws BusinessException, RepositoryException {
        logSeparator();
        return ResponseEntity.status(HttpStatus.CREATED).body(debtorsService.create(debtor));
    }

    @PatchMapping("/update_debtor")
    public ResponseEntity<ApplicationResponse> update(@RequestBody UpdateDebtor debtor) throws BusinessException, RepositoryException, SystemException {
        logSeparator();
        return ResponseEntity.ok(debtorsService.update(debtor));
    }

    @DeleteMapping("/delete_debtor")
    public ResponseEntity<ApplicationResponse> delete(@RequestHeader("id") String id) throws BusinessException, RepositoryException {
        logSeparator();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(debtorsService.delete(id));
    }

    private void logSeparator() {
        log.info("====================================================================================================");
    }
}
