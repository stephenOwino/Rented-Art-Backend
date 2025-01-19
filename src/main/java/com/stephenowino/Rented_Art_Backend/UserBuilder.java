package com.stephenowino.Rented_Art_Backend;


import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.Rental;
import com.stephenowino.Rented_Art_Backend.Entity.Role;
import com.stephenowino.Rented_Art_Backend.Entity.User;

import java.util.Set;

public class UserBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Role role;
        private String bio;
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

        public UserBuilder artPieces(Set<ArtPiece> artPieces) {
                this.artPieces = artPieces;
                return this;
        }

        public UserBuilder rentals(Set<Rental> rentals) {
                this.rentals = rentals;
                return this;
        }

        public User build() {
                return new User(id, firstName, lastName, email, password, role, bio, artPieces, rentals);
        }
}
