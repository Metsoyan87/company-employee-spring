package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Controller
public class EmployeeController {

    @Autowired
    private CompanyRepository companyRepository;
    @Value("${company-employee-spring.images.folder}")
    private String folderPath;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee")
    public String employees(ModelMap modelMap) {
        List<Employee> employees = employeeRepository.findAll();
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("employees", employees);
        modelMap.addAttribute("companies", companies);
        return "employee";
    }

    @GetMapping("/employee/add")
    public String addCompanyPage(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("/employee/add")
    public String addEmployeeImage(@ModelAttribute Employee employee,
                                   @RequestParam(value = "file") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setPicUrl(fileName);
        }
       Optional<Company> byId = companyRepository.findById(employee.getCompany().getId());
        employeeRepository.save(employee);
       Company company = byId.get();
        company.setSize(company.getSize() + 1);
       companyRepository.save(company);
        return "redirect:/employee";

    }

    @GetMapping(value = "/employee/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/employee/delete")
    public String delete(@RequestParam("id") int id) {
        employeeRepository.deleteById(id);
        return "redirect:/employee";
    }
}
