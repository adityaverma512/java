package com.example.gradle.Converter;

import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public Employee dtoToEntity(EmployeeDto dto) {
        if (dto == null) return null;

        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .department(dto.getDepartment())
                .salary(dto.getSalary())
                .build();
    }

    public EmployeeDto entityToDto(Employee employee) {
        if (employee == null) return null;

        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }
}

