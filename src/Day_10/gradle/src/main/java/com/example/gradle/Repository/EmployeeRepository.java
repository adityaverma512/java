package com.example.gradle.Repository;

import com.example.gradle.Entity.Employee;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends SpannerRepository<Employee, String> {
}

