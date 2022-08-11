package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.dto.request.UpdateQtyCart;
import team.kucing.anabulshopcare.dto.response.CartResponse;
import team.kucing.anabulshopcare.entity.UserApp;

import java.util.UUID;

public interface CartService {
    CartResponse createCart(CartRequest cartRequest);

    void deleteCart(UUID id);

    CartResponse updateQtyCart(UpdateQtyCart updateQtyCart, UUID id);

    void deleteCartCustom(UserApp userApp);
}