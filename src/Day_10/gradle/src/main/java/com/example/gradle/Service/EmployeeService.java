package com.example.gradle.Service;


import com.example.gradle.Dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto dto);
    EmployeeDto getEmployeeById(String id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(String id, EmployeeDto dto);
    void deleteEmployee(String id);
}

