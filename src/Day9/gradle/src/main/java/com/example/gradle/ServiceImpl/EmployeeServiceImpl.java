package com.example.gradle.ServiceImpl;

import com.example.gradle.Entity.Employee;
import com.example.gradle.Mapper.EmployeeMapper;
import com.example.gradle.Repository.EmployeeRepository;
import com.example.gradle.Service.EmployeeService;
import com.example.gradle.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final KafkaProducerServiceImpl kafkaProducerService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, KafkaProducerServiceImpl kafkaProducerService) {
        this.employeeRepository = employeeRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public String addEmployee(EmployeeDto employeeDto) throws JsonProcessingException {
        Employee employee = EmployeeMapper.toEntity(employeeDto);
        employeeRepository.save(employee);
        String payload = new ObjectMapper().writeValueAsString(employeeDto);
        kafkaProducerService.sendMessage("employee-topic", payload);
        return "Employee created , and event is sent to Kafka.";
    }
}
