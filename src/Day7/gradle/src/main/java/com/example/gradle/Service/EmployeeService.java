package com.example.gradle.Service;

import com.example.gradle.DTO.EmployeeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
    void importEmployeesFromCsv(MultipartFile file) throws IOException;
    Optional<EmployeeDto> getEmployeeByEmail(String email);
    List<EmployeeDto> getEmployeesByDepartment(String department);
    List<EmployeeDto> getTop3HighestPaidEmployees();

}
