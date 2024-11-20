package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.CreateDebtor;
import org.example.model.UpdateDebtor;
import org.example.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/debtors")
public class Controller {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/fetch_debtors")
    public ResponseEntity<ApplicationResponse> fetchUsers() throws BusinessException, RepositoryException {
        log.info("Fetching Users");
        return ResponseEntity.ok(applicationService.fetchUsers());
    }

    @GetMapping("/fetch_debtor")
    public ResponseEntity<ApplicationResponse> fetchUser(@RequestHeader("id") String id) throws RepositoryException, BusinessException {
        log.info(String.format("Fetching User with id %s", id));
        return ResponseEntity.ok(applicationService.fetchUser(id));
    }

    @PostMapping("/create_debtor")
    public ResponseEntity<ApplicationResponse> create(@RequestBody CreateDebtor debtor) throws BusinessException, RepositoryException {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.create(debtor));
    }

    @PatchMapping("/update_debtor")
    public ResponseEntity<ApplicationResponse> update(@RequestBody UpdateDebtor debtor) throws BusinessException, RepositoryException, SystemException {
        return ResponseEntity.ok(applicationService.update(debtor));
    }

    @DeleteMapping("/delete_debtor")
    public ResponseEntity<ApplicationResponse> delete(@RequestHeader("id") String id) throws BusinessException, RepositoryException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(applicationService.delete(id));
    }
}
