package com.example.gradle.ServiceImpl;

import com.example.gradle.Converter.EmployeeConverter;
import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;
import com.example.gradle.Repository.EmployeeRepository;
import com.example.gradle.Service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeConverter converter;

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee employee = converter.dtoToEntity(dto);
        Employee saved = repository.save(employee);
        return converter.entityToDto(saved);
    }

    @Override
    public List<EmployeeDto> getHighSalaryEmployees(Double minSalary) {
        return repository.findBySalaryGreaterThan(minSalary)
                .stream()
                .map(converter::entityToDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public EmployeeDto updateEmployeeSalary(Long id, Double newSalary) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setSalary(newSalary);

        return converter.entityToDto(employee);
    }

}

