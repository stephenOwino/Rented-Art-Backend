package com.stephenowino.Rented_Art_Backend.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.stephenowino.Rented_Art_Backend.Order;
import com.stephenowino.Rented_Art_Backend.Service.PayPalService;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PayPalController {

        private static final Logger logger = LoggerFactory.getLogger(PayPalController.class);

        private static final String SUCCESS_URL = "pay/success";
        private static final String CANCEL_URL = "pay/cancel";

        private final PayPalService service;

        // Set the base URL dynamically from application.properties or directly in code
        @Value("${app.baseUrl}")
        private String baseUrl = "https://rented-art-server.onrender.com"; // Set your production URL here

        public PayPalController(PayPalService service) {
                this.service = service;
        }

        @PostMapping("/")
        public String showHomePage() {
                return "home"; // This will render the home page (may need to replace with a real frontend page)
        }

        @PostMapping("/pay")
        public String payment(@ModelAttribute("order") Order order) {
                try {
                        Payment payment = service.createPayment(
                                order.getPrice(),
                                order.getCurrency(),
                                order.getMethod(),
                                order.getIntent(),
                                order.getDescription(),
                                baseUrl + "/" + CANCEL_URL,
                                baseUrl + "/" + SUCCESS_URL
                        );

                        String approvalUrl = getApprovalUrl(payment);
                        if (approvalUrl != null) {
                                return "redirect:" + approvalUrl;
                        }
                } catch (PayPalRESTException e) {
                        logger.error("Error during payment creation: {}", e.getMessage(), e);
                }
                return "redirect:/"; // In case of failure, redirect back to the homepage
        }

        private String getApprovalUrl(Payment payment) {
                for (Links link : payment.getLinks()) {
                        if ("approval_url".equals(link.getRel())) {
                                return link.getHref();
                        }
                }
                return null; // If no approval URL found, return null
        }

        @GetMapping(value = CANCEL_URL)
        public String cancelPay() {
                return "cancel"; // This would be your "cancel" page URL to show to the user
        }

        @GetMapping(value = SUCCESS_URL)
        public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
                try {
                        Payment payment = service.executePayment(paymentId, payerId);
                        logger.info("Payment details: {}", payment.toJSON());

                        if ("approved".equals(payment.getState())) {
                                return "success"; // Show success page if payment is approved
                        } else {
                                logger.warn("Payment was not approved. State: {}", payment.getState());
                        }
                } catch (PayPalRESTException e) {
                        logger.error("Error during payment execution: {}", e.getMessage(), e);
                }
                return "redirect:/"; // If payment fails, redirect back to the homepage
        }
}
