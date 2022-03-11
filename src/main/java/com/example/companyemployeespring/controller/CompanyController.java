package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/companies")
    public String companiesPage(ModelMap map) {
        map.addAttribute("companies",companyService.findAll());
        return "companies";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        companyService.deleteById(id);
        return "redirect:/companies";
    }

    @GetMapping("/addCompanies")
    public String addCompanyPage() {
        return "saveCompany";
    }

    @PostMapping("/addCompanies")
    public String addCompany(@ModelAttribute Company company) {
        companyService.save(company);
        return "redirect:/companies";
    }


    @GetMapping("/editCompany/{id}")
    public String editCompaniesPage(ModelMap map, @PathVariable("id") int id) {
        map.addAttribute("company", companyService.findById(id));
            return "saveCompany";


    }
}
