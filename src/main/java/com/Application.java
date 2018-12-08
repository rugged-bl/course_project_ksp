package com;

import com.bank.data.AccountRepository;
import com.bank.data.OperationRepository;
import com.bank.data.entities.Account;
import com.bank.data.entities.AccountType;
import com.bank.data.entities.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(AccountRepository accountRepository, OperationRepository operationRepository) {
        return (args) -> {
            //operationRepository.save(operation);
            //operation.setId(1);
            accountRepository.save(new Account(1000,
                    new AccountType("credit", "cash 123", "qwerty",
                            AccountType.Type.CURRENT_ACCOUNT, "no closable")));
            accountRepository.save(new Account(1223,
                    new AccountType("bill", "cash 444", "qwerty",
                            AccountType.Type.ANOTHER_ACCOUNT, "closable")));

            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Account customer : accountRepository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            accountRepository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });

            Operation operation1 = new Operation(Operation.Type.CHARGE, 123);
            Operation operation2= new Operation(Operation.Type.WITHDRAW, 333);

            accountRepository.findById(1L).map(account -> {
                operation1.setAccount(account);
                operation2.setAccount(account);
                operationRepository.save(operation1);
                return operationRepository.save(operation2);
            });

            log.info("Operations found with findAll():");
            log.info("-------------------------------");
            for (Operation operation : operationRepository.findAll()) {
                log.info(operation.toString());
            }
            log.info("");

            operationRepository.findByAccountId(1L)
                    .forEach(customer -> {
                        log.info("Operation found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });
        };
    }
}