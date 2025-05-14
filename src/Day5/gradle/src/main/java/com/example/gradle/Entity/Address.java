package com.example.gradle.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String city;
        private String state;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        private User user;
}


