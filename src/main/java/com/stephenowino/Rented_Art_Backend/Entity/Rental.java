package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Date startDate;
        private Date endDate;
        private BigDecimal totalPrice;

        @ManyToOne
        @JoinColumn(name = "art_piece_id")
        private ArtPiece artPiece;

        @ManyToOne
        @JoinColumn(name = "renter_id")
        private User renter;

        @Enumerated(EnumType.STRING)
        private RentalStatus status;

        // Builder pattern manually added
        public static RentalBuilder builder() {
                return new RentalBuilder();
        }

        public static class RentalBuilder {
                private Long id;
                private Date startDate;
                private Date endDate;
                private BigDecimal totalPrice;
                private ArtPiece artPiece;
                private User renter;
                private RentalStatus status;

                public RentalBuilder id(Long id) {
                        this.id = id;
                        return this;
                }

                public RentalBuilder startDate(Date startDate) {
                        this.startDate = startDate;
                        return this;
                }

                public RentalBuilder endDate(Date endDate) {
                        this.endDate = endDate;
                        return this;
                }

                public RentalBuilder totalPrice(BigDecimal totalPrice) {
                        this.totalPrice = totalPrice;
                        return this;
                }

                public RentalBuilder artPiece(ArtPiece artPiece) {
                        this.artPiece = artPiece;
                        return this;
                }

                public RentalBuilder renter(User renter) {
                        this.renter = renter;
                        return this;
                }

                public RentalBuilder status(RentalStatus status) {
                        this.status = status;
                        return this;
                }

                public Rental build() {
                        Rental rental = new Rental();
                        rental.id = this.id;
                        rental.startDate = this.startDate;
                        rental.endDate = this.endDate;
                        rental.totalPrice = this.totalPrice;
                        rental.artPiece = this.artPiece;
                        rental.renter = this.renter;
                        rental.status = this.status;
                        return rental;
                }
        }

        public enum RentalStatus {
                ACTIVE,
                COMPLETED,
                CANCELLED
        }
}

