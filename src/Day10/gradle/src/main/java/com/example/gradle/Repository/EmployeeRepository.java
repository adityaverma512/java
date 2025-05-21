package com.example.gradle.Repository;

import com.example.gradle.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // employee by email
    Optional<Employee> findByEmail(String email);

    // employees by department
    List<Employee> findByDepartment(String department);

    // top 3 highest paid employees
    List<Employee> findTop3ByOrderBySalaryDesc();
}
