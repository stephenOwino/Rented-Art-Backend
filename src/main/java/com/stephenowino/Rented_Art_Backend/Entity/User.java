package com.stephenowino.Rented_Art_Backend.Entity;



import com.stephenowino.Rented_Art_Backend.UserBuilder;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
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
        private Role role; // Enum for ARTIST, RENTER

        private String bio; // Optional bio for artist

        @OneToMany(mappedBy = "artist")
        private Set<ArtPiece> artPieces; // Art pieces created by the user (if artist)

        @OneToMany(mappedBy = "renter")
        private Set<Rental> rentals; // Rentals made by the user (if renter)

        public User() {
        }

        // Private constructor to ensure the builder is used
        public User(Long id, String firstName, String lastName, String email, String password, Role role, String bio, Set<ArtPiece> artPieces, Set<Rental> rentals) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
                this.role = role;
                this.bio = bio;
                this.artPieces = artPieces;
                this.rentals = rentals;
        }

        public User(String firstName, String lastName, String email, String password, Role role, String bio) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
                this.role = role;
                this.bio = bio;
        }

        // Getter methods
        public Long getId() {
                return id;
        }

        public String getFirstName() {
                return firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }

        public Role getRole() {
                return role;
        }

        public String getBio() {
                return bio;
        }

        public Set<ArtPiece> getArtPieces() {
                return artPieces;
        }

        public Set<Rental> getRentals() {
                return rentals;
        }

        // Setter methods
        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setRole(Role role) {
                this.role = role;
        }

        public void setBio(String bio) {
                this.bio = bio;
        }

        public void setArtPieces(Set<ArtPiece> artPieces) {
                this.artPieces = artPieces;
        }

        public void setRentals(Set<Rental> rentals) {
                this.rentals = rentals;
        }

        // Builder method
        public static UserBuilder builder() {
                return new UserBuilder();
        }
}
