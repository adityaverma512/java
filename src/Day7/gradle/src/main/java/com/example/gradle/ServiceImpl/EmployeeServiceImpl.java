package com.example.gradle.ServiceImpl;

import com.example.gradle.DTO.EmployeeDto;
import com.example.gradle.Entity.Employee;
import com.example.gradle.Exceptions.EmployeeNotFoundException;
import com.example.gradle.Exceptions.InvalidFileFormatException;
import com.example.gradle.Mapper.EmployeeMapper;
import com.example.gradle.Repository.EmployeeRepository;
import com.example.gradle.Service.EmployeeService;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.toEntity(employeeDto);
        Employee saved = repository.save(employee);
        return EmployeeMapper.toDto(saved);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return repository.findAll()
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
        return EmployeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

        // Update fields
        if (employeeDto.getName() != null) employee.setName(employeeDto.getName());
        if (employeeDto.getEmail() != null) employee.setEmail(employeeDto.getEmail());
        if (employeeDto.getDepartment() != null) employee.setDepartment(employeeDto.getDepartment());
        if (employeeDto.getSalary() != null) employee.setSalary(employeeDto.getSalary());

        Employee updated = repository.save(employee);
        return EmployeeMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
        repository.delete(employee);
    }
    @Override
    public Optional<EmployeeDto> getEmployeeByEmail(String email) {
        return repository.findByEmail(email)
                .map(EmployeeMapper::toDto);
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartment(String department) {
        return repository.findByDepartment(department)
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getTop3HighestPaidEmployees() {
        return repository.findTop3ByOrderBySalaryDesc()
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public void importEmployeesFromCsv(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();

        if (filename == null || (!filename.endsWith(".csv") && !filename.endsWith(".xls") && !filename.endsWith(".xlsx"))) {
            throw new InvalidFileFormatException("Only CSV or Excel (.xls/.xlsx) files are allowed");
        }

        if (filename.endsWith(".csv")) {
            parseCsv(file);
        } else {
            parseExcel(file);
        }
    }
    private void parseCsv(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length >= 3) {
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
    }

    private void parseExcel(MultipartFile file) throws IOException {
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

                // Skip header
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
                return "";
            default:
                return "";
        }
    }

}
