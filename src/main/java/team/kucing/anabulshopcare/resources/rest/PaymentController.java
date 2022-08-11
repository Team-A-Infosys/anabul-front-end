package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.dto.request.PaymentRequest;
import team.kucing.anabulshopcare.entity.Payment;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.service.PaymentService;

import java.awt.*;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@Tag(name = "09. Payment Controller")
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/payment-gateway/create")
    public ResponseEntity<Object> createPayment(@RequestBody PaymentRequest request) {
        Payment response = this.paymentService.createPayment(request);
        return ResponseHandler.generateResponse("Success Create Payment Gateway", HttpStatus.OK, response);
    }

    @GetMapping("/payments")
    public ResponseEntity<Object> getPayments(){
        log.info("success get All Payments");
        List<Payment> response = this.paymentService.getAllPayment();
        return ResponseHandler.generateResponse("success get All Payments",HttpStatus.OK, response);
    }
}