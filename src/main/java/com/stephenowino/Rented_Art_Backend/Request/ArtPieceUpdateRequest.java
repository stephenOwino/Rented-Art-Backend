package com.stephenowino.Rented_Art_Backend.Request;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArtPieceUpdateRequest {
        private Long id;
        private String name;
        private String artist;  // Artist of the art piece
        private BigDecimal price;
        private int inventory;
        private String description;


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getArtist() {
                return artist;
        }

        public void setArtist(String artist) {
                this.artist = artist;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }

        public int getInventory() {
                return inventory;
        }

        public void setInventory(int inventory) {
                this.inventory = inventory;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }
}
