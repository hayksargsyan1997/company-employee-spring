package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;


    @GetMapping("/companies")
    public String companiesPage(ModelMap map) {
        List<Company> companies = companyRepository.findAll();
        map.addAttribute("companies", companies);
        return "companies";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }

    @GetMapping("/addCompanies")
    public String addCompanyPage() {
        return "saveCompany";
    }

    @PostMapping("/addCompanies")
    public String addCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }


    @GetMapping("/editCompany/{id}")
    public String editCompaniesPage(ModelMap map, @PathVariable("id") int id) {
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isEmpty()) {
            map.addAttribute("company", company.get());
            return "saveCompany";
        } else {
            return "redirect:/companies";
        }

    }
}
