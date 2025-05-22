package com.example.gradle.EmployeeConverter;

import com.example.gradle.Dto.EmployeeDto;
import com.example.gradle.Entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public EmployeeDto toDto(Employee entity) {
        return EmployeeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .department(entity.getDepartment())
                .salary(entity.getSalary())
                .build();
    }

    public Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .department(dto.getDepartment())
                .salary(dto.getSalary())
                .build();
    }
}

