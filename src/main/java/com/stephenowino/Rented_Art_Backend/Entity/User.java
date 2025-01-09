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

        private String profilePicture; // Optional profile picture

        private String bio; // Optional bio for artist

        @OneToMany(mappedBy = "user")
        private Set<ArtPiece> artPieces; // Art pieces created by the user (if artist)

        @OneToMany(mappedBy = "renter")
        private Set<Rental> rentals; // Rentals made by the user (if renter)

        public enum Role {
                ARTIST,
                RENTER
        }
}

