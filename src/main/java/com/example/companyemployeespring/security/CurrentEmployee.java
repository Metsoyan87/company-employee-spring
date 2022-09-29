package com.example.companyemployeespring.security;

import com.example.companyemployeespring.entity.Employee;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


public class CurrentEmployee extends User {

    private Employee employee;

    public CurrentEmployee(Employee employee) {
        super(employee.getEmail(),
                employee.getPassword(),
                AuthorityUtils.createAuthorityList(employee.getPosition().name()));
        this.employee = employee;
    }
}
