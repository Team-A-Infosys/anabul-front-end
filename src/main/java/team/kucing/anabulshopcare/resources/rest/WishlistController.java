package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.dto.response.WishlistResponse;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.service.WishlistService;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "04. Wishlist Controller")
public class WishlistController {
    private final WishlistService wishlistService;

//    @PostMapping("/wishlist/add")
//    public ResponseEntity<Object> addWishlist(@RequestBody WishlistRequest wishlistRequest){
//        WishlistResponse response = this.wishlistService.createWishlist(wishlistRequest);
//        log.info("New Item Added To Wishlist");
//        return ResponseHandler.generateResponse("Success add product to wishlist", HttpStatus.OK, response);
//    }

    @DeleteMapping("/wishlist/{id}/delete")
    public ResponseEntity<Object> deleteWishlist(@PathVariable(value = "id") Long id){
        log.info("successfully removed the product from wishlist");
        this.wishlistService.deleteWishlist(id);
        return ResponseHandler.generateResponse("Success delete your wishlist", HttpStatus.OK, null);
    }
}