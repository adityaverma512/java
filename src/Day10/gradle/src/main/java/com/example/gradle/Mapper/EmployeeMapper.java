package com.example.gradle.Mapper;


import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }

    public static Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .salary(dto.getSalary())
                .build();
    }
}
