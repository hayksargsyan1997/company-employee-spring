package com.example.companyemployeespring.service;

import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Value("${companyEmployee.upload.path}")
    private String imagePath;

    public Employee saveEmployeeWithimages(Employee employee, MultipartFile uploadedFile) throws IOException {
        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadedFile.transferTo(newFile);
            employee.setPicUrl(fileName);
        }
        employeeRepository.save(employee);
        return employee;
    }


    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }

    public Employee findById(int id) {
        return employeeRepository.getById(id);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
