package com.stephenowino.Rented_Art_Backend.exception;


public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
                super(message);
        }
}
