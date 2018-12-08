package com.bank.controllers;

import com.bank.data.ClientRepository;
import com.bank.data.DepartmentRepository;
import com.bank.data.entities.Client;
import com.bank.util.ExceptionFormattingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping(value = "/departments/{departmentId}/clients/")
    public List<Client> getAllClientsByDepartmentId(@PathVariable(value = "departmentId") Long departmentId) {
        return clientRepository.findByDepartmentId(departmentId);
    }

    @PostMapping(value = "/departments/{departmentId}/clients/")
    public Client createClient(@PathVariable(value = "departmentId") Long departmentId,
                               @Valid @RequestBody Client client) {
        departmentRepository.findById(departmentId).map(department -> {
            client.setDepartment(department);
            return clientRepository.save(client);
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(departmentId));

        return client;
    }

    @GetMapping(value = "/clients/")
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PutMapping("/clients/{clientId}")
    public Client updateClient(@PathVariable(value = "clientId") Long clientId,
                               @Valid @RequestBody Client postRequest) {
        return clientRepository.findById(clientId).map(client -> {
            client.setName(postRequest.getName());
            return clientRepository.save(client);
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(clientId));
    }


    @DeleteMapping("/clients/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientId) {
        return clientRepository.findById(clientId).map(client -> {
            clientRepository.delete(client);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(clientId));
    }
}