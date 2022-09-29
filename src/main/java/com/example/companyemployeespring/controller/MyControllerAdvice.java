package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.security.CurrentEmployee;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {

    @ModelAttribute(name = "currentUser")
    public String currentUser(@AuthenticationPrincipal CurrentEmployee currentEmployee) {
        if (currentEmployee != null) {
            return currentEmployee.getUsername();
        }
        return null;
    }

}
