package com.example.gradle.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Department is mandatory")
    @Size(min = 2, max = 20, message = "Department name must be between 2 and 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Department must contain only letters and spaces")
    private String department;

    @NotNull(message = "Salary is mandatory")
    @Positive(message = "Salary must be a positive number")
    private Double salary;
}
