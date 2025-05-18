package com.example.gradle.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private int id;

    @NotBlank(message = "City is mandatory")
    @Size(min = 3, max = 20, message = "City must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "City must contain only letters and spaces")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(min = 3, max = 20, message = "State must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "State must contain only letters and spaces")
    private String state;
}

