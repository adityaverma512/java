package com.example.gradle.Entity;

import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import lombok.*;

@Table(name = "employee") // Match table name in Spanner
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @PrimaryKey
    private String id;

    private String name;

    private String department;

    private Long salary; // Spanner's INT64 maps to Java Long
}
