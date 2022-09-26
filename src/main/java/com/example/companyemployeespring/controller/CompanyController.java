package com.example.companyemployeespring.controller;


import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company")
    public String company(ModelMap modelMap) {
        List<Company> all = companyRepository.findAll();
        modelMap.addAttribute("companies", all);
        return "company";
    }

    @GetMapping("/company/add")
    public String addCompanyPage() {
        return "addCompany";
    }

    @PostMapping("/company/add")
    public String addCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/company";
    }

    @GetMapping("/company/delete")
    public String delete(@RequestParam("id") int id) {
        companyRepository.deleteById(id);
        return "redirect:/company";
    }
}
