package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.PaymentRequest;
import team.kucing.anabulshopcare.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayment(PaymentRequest request);

    List<Payment> getAllPayment();
}