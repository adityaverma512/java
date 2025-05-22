package com.example.gradle.Dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    @NotBlank(message = "ID must not be blank")
    private String id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Department must not be blank")
    private String department;

    @NotNull(message = "Salary must not be null")
    @Min(value = 0, message = "Salary must be non-negative")
    private Long salary;
}
