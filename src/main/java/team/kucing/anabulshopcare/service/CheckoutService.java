package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;
import team.kucing.anabulshopcare.dto.response.CheckoutResponse;

import java.util.UUID;

public interface CheckoutService {
    CheckoutResponse createCheckout(UUID id, CheckoutRequest checkoutRequest);

    void cancelCheckout(UUID id);

    CheckoutResponse confirmPayment(UUID id);
}