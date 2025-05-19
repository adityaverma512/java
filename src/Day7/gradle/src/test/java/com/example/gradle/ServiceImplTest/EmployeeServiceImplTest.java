package com.example.gradle.ServiceImplTest;

import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;
import com.example.gradle.Exceptions.EmployeeNotFoundException;
import com.example.gradle.Mapper.EmployeeMapper;
import com.example.gradle.Repository.EmployeeRepository;
import com.example.gradle.ServiceImpl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock private EmployeeRepository repository;
    @InjectMocks private EmployeeServiceImpl service;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John", "john@example.com", "HR", 10000.0);
        employeeDto = new EmployeeDto(1L, "John", "john@example.com", "HR", 10000.0);
    }

    @Test
    void addEmployee_ShouldReturnSavedEmployee() {
        when(repository.save(any())).thenReturn(employee);
        EmployeeDto result = service.addEmployee(employeeDto);
        assertEquals(employeeDto.getEmail(), result.getEmail());
    }

    @Test
    void getAllEmployees_ShouldReturnList() {
        when(repository.findAll()).thenReturn(Arrays.asList(employee));
        List<EmployeeDto> list = service.getAllEmployees();
        assertEquals(1, list.size());
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployee() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        EmployeeDto dto = service.getEmployeeById(1L);
        assertEquals("John", dto.getName());
    }

    @Test
    void getEmployeeById_NonExistent_ThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> service.getEmployeeById(2L));
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(any())).thenReturn(employee);
        EmployeeDto updated = service.updateEmployee(1L, employeeDto);
        assertEquals("John", updated.getName());
    }

    @Test
    void deleteEmployee_ShouldRemoveEmployee() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(repository).delete(any());
        assertDoesNotThrow(() -> service.deleteEmployee(1L));
    }

    @Test
    void getEmployeeByEmail_ShouldReturnDto() {
        when(repository.findByEmail("john@example.com")).thenReturn(Optional.of(employee));
        assertTrue(service.getEmployeeByEmail("john@example.com").isPresent());
    }

    @Test
    void getEmployeesByDepartment_ShouldReturnList() {
        when(repository.findByDepartment("HR")).thenReturn(Arrays.asList(employee));
        List<EmployeeDto> list = service.getEmployeesByDepartment("HR");
        assertEquals(1, list.size());
    }

    @Test
    void getTop3HighestPaidEmployees_ShouldReturnTop3() {
        when(repository.findTop3ByOrderBySalaryDesc()).thenReturn(Arrays.asList(employee));
        List<EmployeeDto> list = service.getTop3HighestPaidEmployees();
        assertEquals(1, list.size());
    }

    @Test
    void importEmployeesFromCsv_InvalidFile_ShouldThrow() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("data.txt");
        assertThrows(RuntimeException.class, () -> service.importEmployeesFromCsv(file));
    }

    @Test
    void importEmployeesFromCsv_CsvFile_ShouldPass() throws IOException {
        String csv = "name,email,department,salary\nJohn,john@example.com,HR,10000";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("data.csv");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(csv.getBytes()));
        service.importEmployeesFromCsv(file);
        verify(repository, times(1)).saveAll(any());
    }
}

