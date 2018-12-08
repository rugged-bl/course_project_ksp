package com.bank.controllers;

import com.bank.data.DepartmentRepository;
import com.bank.data.entities.Department;
import com.bank.util.ExceptionFormattingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/departments")
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping(value = "/")
    public Department createDepartment(@Valid @RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @GetMapping(value = "/")
    public Iterable<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @PutMapping("/{departmentId}")
    public Department updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody Department postRequest) {
        return departmentRepository.findById(departmentId).map(department -> {
            department.setAddress(postRequest.getAddress());
            department.setTitle(postRequest.getTitle());
            return departmentRepository.save(department);
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(departmentId));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long clientId) {
        return departmentRepository.findById(clientId).map(department -> {
            departmentRepository.delete(department);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(clientId));
    }
}