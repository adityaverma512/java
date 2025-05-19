package com.example.gradle.Controller;

import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto dto) {
        EmployeeDto created = service.addEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> list = service.getAllEmployees();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeDto dto = service.getEmployeeById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        EmployeeDto updated = service.updateEmployee(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/email")
    public ResponseEntity<EmployeeDto> getByEmail(@RequestParam String email) {
        return service.getEmployeeByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department")
    public ResponseEntity<List<EmployeeDto>> getByDepartment(@RequestParam String department) {
        return ResponseEntity.ok(service.getEmployeesByDepartment(department));
    }

    @GetMapping("/top-salaries")
    public ResponseEntity<List<EmployeeDto>> getTop3BySalary() {
        return ResponseEntity.ok(service.getTop3HighestPaidEmployees());
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadEmployeesFromCsv(@RequestParam("file") MultipartFile file) throws IOException {
        service.importEmployeesFromCsv(file);
        return ResponseEntity.ok("Employees imported successfully");
    }

}
