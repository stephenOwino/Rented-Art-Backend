package com.stephenowino.Rented_Art_Backend.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "rentals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Date startDate; // Start date of the rental

        private Date endDate; // End date of the rental

        private Double totalPrice; // Total price for the rental duration

        @ManyToOne
        @JoinColumn(name = "art_piece_id")
        private ArtPiece artPiece; // The rented art piece

        @ManyToOne
        @JoinColumn(name = "renter_id")
        private User renter; // The renter who rented the art piece

        @Enumerated(EnumType.STRING)
        private RentalStatus status; // Rental status (active, completed, etc.)

        public enum RentalStatus {
                ACTIVE,
                COMPLETED,
                CANCELLED
        }
}
