package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("${companyEmployee.upload.path}")
    private String imagePath;


    @GetMapping("/employees")
    public String employeesPage(ModelMap map) {
        map.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {

        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/addEmployees")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies", employeeService.findAll());
        return "saveEmployee";
    }

    @PostMapping("/addEmployees")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("picture") MultipartFile uploadFile) throws IOException {
       employeeService.saveEmployeeWithimages(employee,uploadFile);
        return "redirect:/employees";

    }


    @GetMapping("/getImage")
    public @ResponseBody
    byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }

}
