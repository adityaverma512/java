package com.example.gradle.IntegrationTest;

import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;
import com.example.gradle.Repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository repository;

    private String baseUrl = "/api/employees";

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        Employee emp1 = new Employee(null, "John Doe", "john@example.com", "IT", 80000.0);
        Employee emp2 = new Employee(null, "Jane Smith", "jane@example.com", "HR", 90000.0);
        Employee emp3 = new Employee(null, "Bob Johnson", "bob@example.com", "IT", 95000.0);
        Employee emp4 = new Employee(null, "Alice Brown", "alice@example.com", "Finance", 120000.0);
        repository.saveAll(Arrays.asList(emp1, emp2, emp3, emp4));
    }

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void testAddEmployee() {
        EmployeeDto newEmp = new EmployeeDto(null, "Mike Green", "mike@example.com", "IT", 85000.0);

        ResponseEntity<EmployeeDto> response = restTemplate.postForEntity(baseUrl +"/add", newEmp, EmployeeDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Mike Green", response.getBody().getName());
    }

    @Test
    public void testGetAllEmployees() {
        ResponseEntity<EmployeeDto[]> response = restTemplate.getForEntity(baseUrl+"/all", EmployeeDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 4);
    }

    @Test
    public void testGetEmployeeById() {
        Employee existing = repository.findAll().get(0);

        ResponseEntity<EmployeeDto> response = restTemplate.getForEntity(baseUrl + "/" + existing.getId(), EmployeeDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existing.getName(), response.getBody().getName());
    }

    @Test
    public void testUpdateEmployee() {
        Employee existing = repository.findAll().get(0);
        EmployeeDto updateDto = new EmployeeDto(null, "John Updated", null, null, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeDto> request = new HttpEntity<>(updateDto, headers);

        ResponseEntity<EmployeeDto> response = restTemplate.exchange(
                baseUrl + "/" + existing.getId(),
                HttpMethod.PUT,
                request,
                EmployeeDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Updated", response.getBody().getName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee existing = repository.findAll().get(0);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/" + existing.getId(),
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully", response.getBody());
        assertFalse(repository.existsById(existing.getId()));
    }

    @Test
    public void testGetEmployeeByEmail_Found() {
        Employee emp = repository.findAll().get(0);

        ResponseEntity<EmployeeDto> response = restTemplate.getForEntity(baseUrl + "/email?email=" + emp.getEmail(), EmployeeDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emp.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void testGetEmployeeByEmail_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/email?email=nonexistent@example.com", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetEmployeesByDepartment() {
        ResponseEntity<EmployeeDto[]> response = restTemplate.getForEntity(baseUrl + "/department?department=IT", EmployeeDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 2);
    }

    @Test
    public void testGetTop3HighestPaidEmployees() {
        ResponseEntity<EmployeeDto[]> response = restTemplate.getForEntity(baseUrl + "/top-salaries", EmployeeDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length <= 3);
        assertTrue(response.getBody()[0].getSalary() >= response.getBody()[1].getSalary());
    }

    @Test
    public void testUploadEmployeesFromCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource("employee_test.csv");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/upload", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Employees imported successfully"));
    }
}
