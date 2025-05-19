package com.example.gradle.ControllerTest;

import com.example.gradle.Controller.EmployeeController;
import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Resource private MockMvc mockMvc;

    @MockBean private EmployeeService service;

    @Test
    void addEmployee_ShouldReturnCreated() throws Exception {
        EmployeeDto dto = new EmployeeDto(1L, "John", "john@example.com", "HR", 10000.0);
        when(service.addEmployee(any())).thenReturn(dto);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "John",
                          "email": "john@example.com",
                          "department": "HR",
                          "salary": 10000
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getAllEmployees_ShouldReturnList() throws Exception {
        when(service.getAllEmployees()).thenReturn(Arrays.asList(new EmployeeDto()));
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() throws Exception {
        when(service.getEmployeeById(1L)).thenReturn(new EmployeeDto());
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateEmployee_ShouldReturnUpdated() throws Exception {
        when(service.updateEmployee(eq(1L), any())).thenReturn(new EmployeeDto());
        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Jane",
                          "email": "jane@example.com",
                          "department": "IT",
                          "salary": 12000
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(service).deleteEmployee(1L);
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getByEmail_ShouldReturnEmployee() throws Exception {
        when(service.getEmployeeByEmail("john@example.com"))
                .thenReturn(Optional.of(new EmployeeDto()));
        mockMvc.perform(get("/api/employees/email?email=john@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    void getByDepartment_ShouldReturnList() throws Exception {
        when(service.getEmployeesByDepartment("HR")).thenReturn(Arrays.asList(new EmployeeDto()));
        mockMvc.perform(get("/api/employees/department?department=HR"))
                .andExpect(status().isOk());
    }

    @Test
    void getTop3BySalary_ShouldReturnList() throws Exception {
        when(service.getTop3HighestPaidEmployees()).thenReturn(Arrays.asList(new EmployeeDto()));
        mockMvc.perform(get("/api/employees/top-salaries"))
                .andExpect(status().isOk());
    }
}

