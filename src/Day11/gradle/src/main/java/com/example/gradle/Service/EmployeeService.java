package com.example.gradle.Service;

import com.example.gradle.DTO.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto dto);
    List<EmployeeDto> getHighSalaryEmployees(Double minSalary);
    EmployeeDto updateEmployeeSalary(Long id, Double newSalary);
}

