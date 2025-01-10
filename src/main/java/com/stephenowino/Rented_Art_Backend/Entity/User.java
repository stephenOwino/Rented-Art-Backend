package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String firstName;

        private String lastName;

        @Column(unique = true)
        private String email;

        private String password; // Encrypted password

        @Enumerated(EnumType.STRING)
        private Role role; // ENUM for Artist, Renter

        private String bio; // Optional bio for artist

        @OneToMany(mappedBy = "artist")
        private Set<ArtPiece> artPieces; // Art pieces created by the user (if artist)

        @OneToMany(mappedBy = "renter")
        private Set<Rental> rentals; // Rentals made by the user (if renter)

        @Transient
        private String confirmPassword; // Confirm password field for validation purposes

        public enum Role {
                ARTIST,
                RENTER
        }

        // Getters and Setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }

        public String getBio() {
                return bio;
        }

        public void setBio(String bio) {
                this.bio = bio;
        }

        public Set<ArtPiece> getArtPieces() {
                return artPieces;
        }

        public void setArtPieces(Set<ArtPiece> artPieces) {
                this.artPieces = artPieces;
        }

        public Set<Rental> getRentals() {
                return rentals;
        }

        public void setRentals(Set<Rental> rentals) {
                this.rentals = rentals;
        }

        public String getConfirmPassword() {
                return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
                this.confirmPassword = confirmPassword;
        }
}
