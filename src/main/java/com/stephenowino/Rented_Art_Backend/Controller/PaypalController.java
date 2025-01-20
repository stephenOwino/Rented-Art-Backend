package com.stephenowino.Rented_Art_Backend.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.stephenowino.Rented_Art_Backend.Order;
import com.stephenowino.Rented_Art_Backend.Service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

        @Value("${paypal.success-url}")
        private String successUrl;

        @Value("${paypal.cancel-url}")
        private String cancelUrl;

        // Payment initiation endpoint
        @PostMapping("/pay")
        public String payment(@Validated @ModelAttribute("order") Order order, BindingResult result, Model model) {
                if (result.hasErrors()) {
                        model.addAttribute("error", "Invalid order details. Please check the form and try again.");
                        return "error"; // Return error page if validation fails
                }

                try {
                        Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                                order.getIntent(), order.getDescription(), cancelUrl, successUrl);

                        for (Links link : payment.getLinks()) {
                                if (link.getRel().equals("approval_url")) {
                                        return "redirect:" + link.getHref(); // Redirect to PayPal approval URL
                                }
                        }
                } catch (PayPalRESTException e) {
                        e.printStackTrace();
                        model.addAttribute("error", "There was an error processing your payment. Please try again later.");
                        return "error"; // Redirect to error page if payment creation fails
                }

                return "redirect:/"; // Redirect to home page if no approval URL is found
        }

        // Payment cancellation endpoint
        @GetMapping(value = CANCEL_URL)
        public String cancelPay(Model model) {
                model.addAttribute("message", "Your payment was canceled. Please try again if you wish.");
                return "cancel"; // Redirects to cancel page if payment is canceled
        }

        // Payment success endpoint
        @GetMapping(value = SUCCESS_URL)
        public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model) {
                try {
                        Payment payment = service.executePayment(paymentId, payerId);
                        System.out.println(payment.toJSON()); // Log payment details

                        if (payment.getState().equals("approved")) {
                                model.addAttribute("message", "Payment successful! Thank you for your purchase.");
                                return "success"; // Redirects to success page if payment is approved
                        } else {
                                model.addAttribute("message", "Payment was not approved. Please try again.");
                                return "error"; // Return error page if payment was not approved
                        }
                } catch (PayPalRESTException e) {
                        System.out.println(e.getMessage()); // Log the exception
                        model.addAttribute("error", "An error occurred while processing your payment. Please try again later.");
                        return "error"; // Redirects to error page if payment execution fails
                }
        }
}
