package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.dto.response.WishlistResponse;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;

import java.util.UUID;

public interface WishlistService {
    WishlistResponse createWishlist(UUID productId, String email);

    void deleteWishlist(Long Id);

    void deleteWishlistCustom(Product product, UserApp userApp);
}