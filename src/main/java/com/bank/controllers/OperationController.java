package com.bank.controllers;

import com.bank.data.AccountRepository;
import com.bank.data.OperationRepository;
import com.bank.data.entities.Operation;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.util.ExceptionFormattingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OperationController {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping(value = "/accounts/{accountId}/operations/")
    public List<Operation> getAllOperationsByAccountId(@PathVariable(value = "accountId") Long accountId) {
        return operationRepository.findByAccountId(accountId);
    }

    @PostMapping(value = "/accounts/{accountId}/operations/")
    public Operation createOperation(@PathVariable(value = "accountId") Long accountId,
                                     @Valid @RequestBody Operation operation) {
        accountRepository.findById(accountId).map(account -> {
            operation.setAccount(account);
            return operationRepository.save(operation);
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(accountId));

        return operation;
    }

    @GetMapping(value = "/operations/")
    public Iterable<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @PutMapping("/operations/{operationId}")
    public Operation updateOperation(@PathVariable(value = "operationId") Long operationId,
                                     @Valid @RequestBody Operation operationRequest) {
        return operationRepository.findById(operationId).map(operation -> {
            operation.setType(operationRequest.getType());
            operation.setAmount(operationRequest.getAmount());
            return operationRepository.save(operation);
        }).orElseThrow(() -> new ResourceNotFoundException("OperationId " + operationId + "not found"));
    }

    @DeleteMapping("/operations/{operationId}")
    public ResponseEntity<?> deleteOperation(@PathVariable(value = "operationId") Long operationId) {
        return operationRepository.findById(operationId).map(operation -> {
            operationRepository.delete(operation);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("OperationId " + operationId + " not found"));
    }
}
