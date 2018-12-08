package com.bank.data;

import com.bank.data.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByDepartmentId(Long departmentId);
    List<Account> findByClientId(Long clientId);
}
