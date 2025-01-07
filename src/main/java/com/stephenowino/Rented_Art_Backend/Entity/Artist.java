package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends User {
        private String portfolioLink; // Link to artist's portfolio
        private String bio; // Artist bio
}

