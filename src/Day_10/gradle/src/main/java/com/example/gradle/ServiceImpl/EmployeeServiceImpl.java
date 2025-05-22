package com.example.gradle.ServiceImpl;

import com.example.gradle.Dto.EmployeeDto;
import com.example.gradle.EmployeeConverter.EmployeeConverter;
import com.example.gradle.Entity.Employee;
import com.example.gradle.Repository.EmployeeRepository;
import com.example.gradle.Service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeConverter converter;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        log.info("Creating employee: {}", dto);
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
            log.debug("Generated new UUID for employee: {}", dto.getId());
        }
        Employee saved = repository.save(converter.toEntity(dto));
        log.info("Employee saved with ID: {}", saved.getId());
        return converter.toDto(saved);
    }

    @Override
    public EmployeeDto getEmployeeById(String id) {
        log.info("Fetching employee by ID: {}", id);
        return repository.findById(id)
                .map(employee -> {
                    log.debug("Employee found: {}", employee);
                    return converter.toDto(employee);})
                .orElseThrow(() -> {
                    log.warn("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with id: " + id);
                });
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        log.info("Fetching all employees");
        Iterable<Employee> employees = repository.findAll();
        List<EmployeeDto> result = StreamSupport.stream(employees.spliterator(), false)
                .map(converter::toDto)
                .collect(Collectors.toList());
        log.debug("Total employees found: {}", result.size());
        return result;
    }

    @Override
    public EmployeeDto updateEmployee(String id, EmployeeDto dto) {
        log.info("Updating employee with ID: {}", id);
        Employee existing = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with id: " + id);
                });

        existing.setName(dto.getName());
        existing.setDepartment(dto.getDepartment());
        existing.setSalary(dto.getSalary());

        Employee updated = repository.save(existing);
        log.info("Employee updated with ID: {}", updated.getId());
        return converter.toDto(updated);
    }

    @Override
    public void deleteEmployee(String id) {
        log.info("Deleting employee with ID: {}", id);
        repository.deleteById(id);
        log.info("Employee with ID {} deleted", id);
    }
}


