package com.example.gradle.Controller;
import com.example.gradle.Service.EmployeeService;
import com.example.gradle.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto) throws JsonProcessingException {
        return ResponseEntity.ok(employeeService.addEmployee(employeeDto));
    }
}
