package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String role; // e.g., "ARTIST" or "RENTER"

        @Column(nullable = false)
        private String email; // Add email

        private String fullname; // Add fullname

        private boolean subscribeToMailingList; // Add mailing list opt-in

        private boolean agreedToRules; // Add agreement to terms

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getFullname() {
                return fullname;
        }

        public void setFullname(String fullname) {
                this.fullname = fullname;
        }

        public boolean isSubscribeToMailingList() {
                return subscribeToMailingList;
        }

        public void setSubscribeToMailingList(boolean subscribeToMailingList) {
                this.subscribeToMailingList = subscribeToMailingList;
        }

        public boolean isAgreedToRules() {
                return agreedToRules;
        }

        public void setAgreedToRules(boolean agreedToRules) {
                this.agreedToRules = agreedToRules;
        }
}

