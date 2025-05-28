package com.example.gradle.Mapper;

import com.example.gradle.Entity.Employee;
import com.example.gradle.dto.EmployeeDto;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee)
    {
        return new EmployeeDto(employee.getName(), employee.getEmail(), employee.getDepartment(), employee.getSalary());
    }
    public static Employee toEntity(EmployeeDto employeeDto)
    {
        return new Employee(employeeDto.getName(), employeeDto.getEmail(), employeeDto.getDepartment(), employeeDto.getSalary());
    }
}
