package com.bank.controllers;

import com.bank.data.AccountRepository;
import com.bank.data.ClientRepository;
import com.bank.data.DepartmentRepository;
import com.bank.data.entities.Account;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.util.ExceptionFormattingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping(value = "/departments/{departmentId}/accounts/")
    public List<Account> getAllAccountsByDepartmentId(@PathVariable(value = "departmentId") Long departmentId) {
        return accountRepository.findByDepartmentId(departmentId);
    }

    @GetMapping(value = "/clients/{clientId}/accounts")
    public List<Account> getAllAccountsByClientId(@PathVariable(value = "clientId") Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @PostMapping(value = "/departments/{departmentId}.{clientId}/accounts/")
    public Account createAccount(@PathVariable(value = "departmentId") Long departmentId,
                                 @PathVariable(value = "clientId") Long clientId,
                                 @Valid @RequestBody Account account) {
        departmentRepository.findById(departmentId).map(department -> {
            account.setDepartment(department);

            clientRepository.findById(clientId).map(client -> {
                account.setClient(client);
                return client;
            }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(clientId));

            return accountRepository.save(account);
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(departmentId));

        return account;
    }

    @GetMapping(value = "/accounts/")
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @PutMapping("/accounts/{accountId}")
    public Account updateAccount(@PathVariable(value = "accountId") Long accountId,
                                 @Valid @RequestBody Account accountRequest) {

        return accountRepository.findById(accountId).map(account -> {
            account.setBalance(accountRequest.getBalance());
            return accountRepository.save(account);
        }).orElseThrow(() -> new ResourceNotFoundException("AccountId " + accountId + "not found"));
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable(value = "accountId") Long accountId) {

        return accountRepository.findById(accountId).map(contact -> {
            accountRepository.delete(contact);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("AccountId " + accountId + " not found"));
    }
}