package com.stephenowino.Rented_Art_Backend.Response;

import java.util.List;

public class ApiResponse {
        private String message;
        private Object data;

        public ApiResponse(String message, Object data) {
                this.message = message;
                this.data = data;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public Object getData() {
                return data;
        }

        public void setData(Object data) {
                this.data = data;
        }

        @Override
        public String toString() {
                return "ApiResponse{" +
                        "message='" + message + '\'' +
                        ", data=" + data +
                        '}';
        }
}
