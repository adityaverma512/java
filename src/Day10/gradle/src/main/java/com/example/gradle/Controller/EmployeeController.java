package com.example.gradle.Controller;

import com.example.gradle.DTO.EmailDto;
import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto dto) {
        logger.info("Adding new employee: {}", dto);
        EmployeeDto created = service.addEmployee(dto);
        logger.info("Employee created with ID: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        logger.info("Fetching all employees");
        List<EmployeeDto> list = service.getAllEmployees();
        logger.info("Found {} employees", list.size());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        logger.info("Fetching employee by ID: {}", id);
        EmployeeDto dto = service.getEmployeeById(id);
        logger.info("Found employee: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        logger.info("Updating employee with ID: {}", id);
        EmployeeDto updated = service.updateEmployee(id, dto);
        logger.info("Employee updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        logger.info("Deleting employee with ID: {}", id);
        service.deleteEmployee(id);
        logger.info("Employee deleted with ID: {}", id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/email")
    public ResponseEntity<EmployeeDto> getByEmail(@RequestParam String email) {
        logger.info("Fetching employee by email: {}", email);
        return service.getEmployeeByEmail(email)
                .map(dto -> {
                    logger.info("Employee found: {}", dto);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> {
                    logger.warn("Employee not found with email: {}", email);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/department")
    public ResponseEntity<List<EmployeeDto>> getByDepartment(@RequestParam String department) {
        logger.info("Fetching employees by department: {}", department);
        List<EmployeeDto> list = service.getEmployeesByDepartment(department);
        logger.info("Found {} employees in department {}", list.size(), department);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/top-salaries")
    public ResponseEntity<List<EmployeeDto>> getTop3BySalary() {
        logger.info("Fetching top 3 highest paid employees");
        List<EmployeeDto> topPaid = service.getTop3HighestPaidEmployees();
        logger.info("Top 3 highest paid employees retrieved");
        return ResponseEntity.ok(topPaid);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadEmployeesFromCsv(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Uploading employee data from CSV file: {}", file.getOriginalFilename());
        service.importEmployeesFromCsv(file);
        logger.info("Employees imported successfully from file: {}", file.getOriginalFilename());
        return ResponseEntity.ok("Employees imported successfully");
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto dto) {
        logger.info("Sending email using Feign client: {}", dto);
        String response = service.emailByFeignClient(dto);
        logger.info("Email sent response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-by-resttemplate")
    public ResponseEntity<String> sendEmailByRestTemplate(@RequestBody EmailDto dto) {
        logger.info("Sending email using RestTemplate to: {}, subject: {}", dto.getTo(), dto.getSubject());
        String response = service.emailByRestTemplate(dto);
        logger.info("Email sent via RestTemplate. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-by-webclient")
    public ResponseEntity<String> sendEmailByWebClient(@RequestBody EmailDto dto) {
        logger.info("Sending email using WebClient to: {}, subject: {}", dto.getTo(), dto.getSubject());
        String response = service.emailByWebClient(dto);
        logger.info("Email sent via WebClient. Response: {}", response);
        return ResponseEntity.ok(response);
    }



}
