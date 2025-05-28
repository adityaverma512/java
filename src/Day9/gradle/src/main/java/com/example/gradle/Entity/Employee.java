package com.example.gradle.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String department;
    private float salary;

    public Employee(String name, String email, String department, float salary) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }
}
