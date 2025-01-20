package com.stephenowino.Rented_Art_Backend.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.stephenowino.Rented_Art_Backend.Order;
import com.stephenowino.Rented_Art_Backend.Service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {

        @Autowired
        PayPalService service;

        public static final String SUCCESS_URL = "pay/success";
        public static final String CANCEL_URL = "pay/cancel";

        @GetMapping("/")
        public String home() {
                return "home"; // Redirects to the home page
        }

        @PostMapping("/pay")
        public String payment(@ModelAttribute("order") Order order) {
                try {
                        Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                                order.getIntent(), order.getDescription(), "http://localhost:9090/" + CANCEL_URL,
                                "http://localhost:9090/" + SUCCESS_URL);
                        for (Links link : payment.getLinks()) {
                                if (link.getRel().equals("approval_url")) {
                                        return "redirect:" + link.getHref(); // Redirects to the PayPal approval URL
                                }
                        }
                } catch (PayPalRESTException e) {
                        e.printStackTrace(); // Log the exception
                }
                return "redirect:/"; // Redirects to the home page if there's an error
        }

        @GetMapping(value = CANCEL_URL)
        public String cancelPay() {
                return "cancel"; // Redirects to the cancel page if payment is canceled
        }

        @GetMapping(value = SUCCESS_URL)
        public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
                try {
                        Payment payment = service.executePayment(paymentId, payerId);
                        System.out.println(payment.toJSON()); // Log the payment details
                        if (payment.getState().equals("approved")) {
                                return "success"; // Redirects to success page if payment is approved
                        }
                } catch (PayPalRESTException e) {
                        System.out.println(e.getMessage()); // Log the exception
                }
                return "redirect:/"; // Redirects to the home page if payment fails
        }
}




