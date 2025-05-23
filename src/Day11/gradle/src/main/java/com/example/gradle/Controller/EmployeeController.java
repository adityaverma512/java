package com.example.gradle.Controller;

import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto dto) {
        EmployeeDto created = service.createEmployee(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/high-salary")
    public ResponseEntity<List<EmployeeDto>> getHighSalaryEmployees(@RequestParam Double minSalary) {
        List<EmployeeDto> result = service.getHighSalaryEmployees(minSalary);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-salary")
    public ResponseEntity<EmployeeDto> updateSalary(@RequestParam Long id, @RequestParam Double newSalary) {
        EmployeeDto updated = service.updateEmployeeSalary(id, newSalary);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}

