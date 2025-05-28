package com.example.gradle.Service;

import com.example.gradle.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EmployeeService {
    String addEmployee(EmployeeDto employeeDto) throws JsonProcessingException;
}

