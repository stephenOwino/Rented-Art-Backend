package com.stephenowino.Rented_Art_Backend.Entity;

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
        private Role role; // ENUM for ARTIST, RENTER

        private String bio; // Optional bio for artist

        private String profilePicture;

        @OneToMany(mappedBy = "artist")
        private Set<ArtPiece> artPieces; // Art pieces created by the user (if artist)

        @OneToMany(mappedBy = "renter")
        private Set<Rental> rentals; // Rentals made by the user (if renter)

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

        public String getProfilePicture() {
                return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
        }

        // Builder Pattern
        public static UserBuilder builder() {
                return new UserBuilder();
        }

        public static class UserBuilder {
                private Long id;
                private String firstName;
                private String lastName;
                private String email;
                private String password;
                private Role role;
                private String bio;
                private String profilePicture;
                private Set<ArtPiece> artPieces;
                private Set<Rental> rentals;

                public UserBuilder id(Long id) {
                        this.id = id;
                        return this;
                }

                public UserBuilder firstName(String firstName) {
                        this.firstName = firstName;
                        return this;
                }

                public UserBuilder lastName(String lastName) {
                        this.lastName = lastName;
                        return this;
                }

                public UserBuilder email(String email) {
                        this.email = email;
                        return this;
                }

                public UserBuilder password(String password) {
                        this.password = password;
                        return this;
                }

                public UserBuilder role(Role role) {
                        this.role = role;
                        return this;
                }

                public UserBuilder bio(String bio) {
                        this.bio = bio;
                        return this;
                }

                public UserBuilder profilePicture(String profilePicture) {
                        this.profilePicture = profilePicture;
                        return this;
                }

                public UserBuilder artPieces(Set<ArtPiece> artPieces) {
                        this.artPieces = artPieces;
                        return this;
                }

                public UserBuilder rentals(Set<Rental> rentals) {
                        this.rentals = rentals;
                        return this;
                }

                public User build() {
                        User user = new User();
                        user.id = this.id;
                        user.firstName = this.firstName;
                        user.lastName = this.lastName;
                        user.email = this.email;
                        user.password = this.password;
                        user.role = this.role;
                        user.bio = this.bio;
                        user.profilePicture = this.profilePicture;
                        user.artPieces = this.artPieces;
                        user.rentals = this.rentals;
                        return user;
                }
        }
}
