package com.stephenowino.Rented_Art_Backend.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.stephenowino.Rented_Art_Backend.Order;
import com.stephenowino.Rented_Art_Backend.Service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/paypal")
public class PaypalController {

        @Autowired
        private PayPalService service;

        private static final String BASE_URL = "https://rented-art.onrender.com";

        @PostMapping("/pay")
        public ResponseEntity<?> initiatePayment(@RequestBody Order order) {
                Map<String, String> response = new HashMap<>();
                try {
                        Payment payment = service.createPayment(
                                order.getPrice(),
                                order.getCurrency(),
                                order.getMethod(),
                                order.getIntent(),
                                order.getDescription(),
                                BASE_URL + "/paypal/cancel",
                                BASE_URL + "/paypal/success"
                        );

                        for (Links link : payment.getLinks()) {
                                if ("approval_url".equals(link.getRel())) {
                                        response.put("redirectUrl", link.getHref());
                                        return ResponseEntity.ok(response);
                                }
                        }
                } catch (PayPalRESTException e) {
                        e.printStackTrace();
                        response.put("error", "Payment initiation failed: " + e.getMessage());
                        return ResponseEntity.badRequest().body(response);
                }

                response.put("error", "Approval URL not found");
                return ResponseEntity.badRequest().body(response);
        }

        @GetMapping("/cancel")
        public ResponseEntity<?> cancelPayment() {
                Map<String, String> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Payment canceled by the user");
                return ResponseEntity.ok(response);
        }

        @GetMapping("/success")
        public ResponseEntity<?> successPayment(@RequestParam("paymentId") String paymentId,
                                                @RequestParam("PayerID") String payerId) {
                Map<String, String> response = new HashMap<>();
                try {
                        Payment payment = service.executePayment(paymentId, payerId);
                        if ("approved".equals(payment.getState())) {
                                response.put("status", "success");
                                response.put("message", "Payment approved");
                                response.put("paymentDetails", payment.toJSON());
                                return ResponseEntity.ok(response);
                        }
                } catch (PayPalRESTException e) {
                        response.put("status", "error");
                        response.put("message", "Payment execution failed: " + e.getMessage());
                        return ResponseEntity.badRequest().body(response);
                }

                response.put("status", "error");
                response.put("message", "Payment not approved");
                return ResponseEntity.badRequest().body(response);
        }
}


