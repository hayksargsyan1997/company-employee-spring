package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${companyEmployee.upload.path}")
    private String imagePath;


    @GetMapping("/employees")
    public String employeesPage(ModelMap map) {
        List<Employee> employees = employeeRepository.findAll();
        map.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {

        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/addEmployees")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies",companyRepository.findAll());
        return "saveEmployee";
    }

    @PostMapping("/addEmployees")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("picture") MultipartFile uploadFile) throws IOException {

        if (!uploadFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadFile.transferTo(newFile);
            employee.setPicUrl(fileName);


        }
        employeeRepository.save(employee);
        return "redirect:/employees";

    }


    @GetMapping("/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream=new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }

}
