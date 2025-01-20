package com.stephenowino.Rented_Art_Backend.exception;



import com.stephenowino.Rented_Art_Backend.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
}
