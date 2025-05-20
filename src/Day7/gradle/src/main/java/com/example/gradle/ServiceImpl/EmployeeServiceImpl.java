package com.example.gradle.ServiceImpl;

import com.example.gradle.DTO.EmailDto;
import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;
import com.example.gradle.Exceptions.EmployeeNotFoundException;
import com.example.gradle.Exceptions.InvalidFileFormatException;
import com.example.gradle.Mapper.EmployeeMapper;
import com.example.gradle.Repository.EmployeeRepository;
import com.example.gradle.Service.EmployeeService;
import com.example.gradle.feignClient.EmailFeignClient;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository repository;
    private final EmailFeignClient emailFeignClient;
    private final RestTemplate restTemplate;
    private final WebClient emailWebClient;
    @Value("${email.service.url:http://localhost:8081}")
    private String emailServiceUrl;
    public EmployeeServiceImpl(EmployeeRepository repository, EmailFeignClient emailFeignClient,RestTemplate restTemplate,WebClient emailWebClient) {
        this.repository = repository;
        this.emailFeignClient = emailFeignClient;
        this.restTemplate=restTemplate;
        this.emailWebClient=emailWebClient;
    }

    @Override
    @Transactional
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        logger.info("Adding new employee: {}", employeeDto);
        Employee employee = EmployeeMapper.toEntity(employeeDto);
        Employee saved = repository.save(employee);
        logger.info("Employee saved with ID: {}", saved.getId());
        return EmployeeMapper.toDto(saved);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        logger.info("Fetching all employees");
        List<EmployeeDto> employees = repository.findAll()
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Total employees fetched: {}", employees.size());
        return employees;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        logger.info("Fetching employee by ID: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee with ID {} not found", id);
                    return new EmployeeNotFoundException("Employee with ID " + id + " not found");
                });
        logger.info("Employee found: {}", employee);
        return EmployeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        logger.info("Updating employee with ID: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee with ID {} not found for update", id);
                    return new EmployeeNotFoundException("Employee with ID " + id + " not found");
                });

        // Update fields
        if (employeeDto.getName() != null) employee.setName(employeeDto.getName());
        if (employeeDto.getEmail() != null) employee.setEmail(employeeDto.getEmail());
        if (employeeDto.getDepartment() != null) employee.setDepartment(employeeDto.getDepartment());
        if (employeeDto.getSalary() != null) employee.setSalary(employeeDto.getSalary());

        Employee updated = repository.save(employee);
        logger.info("Employee updated: {}", updated);
        return EmployeeMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee with ID {} not found for deletion", id);
                    return new EmployeeNotFoundException("Employee with ID " + id + " not found");
                });
        repository.delete(employee);
        logger.info("Employee with ID {} deleted", id);
    }

    @Override
    public String emailByFeignClient(EmailDto emailDto) {
        logger.info("Sending email to: {}", emailDto.getTo());
        String response = emailFeignClient.sendEmail(emailDto);
        logger.info("Email sent, response: {}", response);
        return response;
    }

    @Override
    public String emailByRestTemplate(EmailDto emailDto) {
        logger.info("Sending email via RestTemplate to: {}, subject: {}", emailDto.getTo(), emailDto.getSubject());
        String url = emailServiceUrl + "/api/email/send";
        String response = restTemplate.postForObject(url, emailDto, String.class);
        logger.info("Response from RestTemplate email service: {}", response);
        return response;
    }

    @Override
    public String emailByWebClient(EmailDto emailDto) {
        logger.info("Sending email via WebClient to: {}, subject: {}", emailDto.getTo(), emailDto.getSubject());
        String response = emailWebClient.post()
                .uri("/send")
                .bodyValue(emailDto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        logger.info("Response from WebClient email service: {}", response);
        return response;
    }

    @Override
    public Optional<EmployeeDto> getEmployeeByEmail(String email) {
        logger.info("Fetching employee by email: {}", email);
        return repository.findByEmail(email)
                .map(EmployeeMapper::toDto);
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartment(String department) {
        logger.info("Fetching employees by department: {}", department);
        List<EmployeeDto> employees = repository.findByDepartment(department)
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Found {} employees in department {}", employees.size(), department);
        return employees;
    }

    @Override
    public List<EmployeeDto> getTop3HighestPaidEmployees() {
        logger.info("Fetching top 3 highest paid employees");
        List<EmployeeDto> top3 = repository.findTop3ByOrderBySalaryDesc()
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Top 3 highest paid employees retrieved");
        return top3;
    }

    @Override
    public void importEmployeesFromCsv(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        logger.info("Importing employees from file: {}", filename);

        if (filename == null || (!filename.endsWith(".csv") && !filename.endsWith(".xls") && !filename.endsWith(".xlsx"))) {
            logger.error("Invalid file format for file: {}", filename);
            throw new InvalidFileFormatException("Only CSV or Excel (.xls/.xlsx) files are allowed");
        }

        if (filename.endsWith(".csv")) {
            parseCsv(file);
        } else {
            parseExcel(file);
        }

        logger.info("Employee import completed for file: {}", filename);
    }

    private void parseCsv(MultipartFile file) throws IOException {
        logger.debug("Parsing CSV file: {}", file.getOriginalFilename());
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length >= 4) {
                    Employee emp = new Employee();
                    emp.setName(fields[0].trim());
                    emp.setEmail(fields[1].trim());
                    emp.setDepartment(fields[2].trim());
                    emp.setSalary(Double.parseDouble(fields[3].trim()));
                    employees.add(emp);
                }
            }
        }

        repository.saveAll(employees);
        logger.info("Parsed and saved {} employees from CSV", employees.size());
    }

    private void parseExcel(MultipartFile file) throws IOException {
        logger.debug("Parsing Excel file: {}", file.getOriginalFilename());
        List<Employee> employees = new ArrayList<>();
        Workbook workbook;

        try (InputStream inputStream = file.getInputStream()) {
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            boolean isFirstRow = true;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                Employee emp = new Employee();
                emp.setName(getCellValueAsString(row.getCell(0)));
                emp.setEmail(getCellValueAsString(row.getCell(1)));
                emp.setDepartment(getCellValueAsString(row.getCell(2)));
                emp.setSalary(Double.parseDouble(getCellValueAsString(row.getCell(3))));
                employees.add(emp);
            }
        }

        repository.saveAll(employees);
        logger.info("Parsed and saved {} employees from Excel", employees.size());
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return "";
        }
    }
}
