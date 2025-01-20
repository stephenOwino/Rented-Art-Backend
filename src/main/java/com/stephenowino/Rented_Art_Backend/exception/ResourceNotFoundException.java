package com.stephenowino.Rented_Art_Backend.exception;

public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
                super(message);

        }
}
