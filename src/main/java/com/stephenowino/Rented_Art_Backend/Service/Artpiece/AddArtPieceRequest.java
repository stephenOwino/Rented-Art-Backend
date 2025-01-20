package com.stephenowino.Rented_Art_Backend.Service.Artpiece;

import com.stephenowino.Rented_Art_Backend.Entity.Role;
import com.stephenowino.Rented_Art_Backend.Request.ArtPieceUpdateRequest;

import java.math.BigDecimal;

public class AddArtPieceRequest {

        public ArtPieceUpdateRequest Role;
        private Long id;
        private String name;
        private String brand;
        private BigDecimal price;
        private int inventory;
        private String description;
        private String title;
        private Role role;
        private String stock;


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

        public String getBrand() {
                return brand;
        }

        public void setBrand(String brand) {
                this.brand = brand;
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

        public String getTitle() {
                return title;
        }


        public int getStock() {
                return 0;
        }
}


