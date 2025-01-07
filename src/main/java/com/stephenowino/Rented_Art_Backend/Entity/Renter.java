package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "renters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Renter extends User {
        private String contactNumber; // Renter contact information
        private String address; // Address details
}

