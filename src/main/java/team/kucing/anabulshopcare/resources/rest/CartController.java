package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.dto.request.UpdateQtyCart;
import team.kucing.anabulshopcare.dto.response.CartResponse;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.service.CartService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "05. Cart Controller")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/addtocart")
    public ResponseEntity<Object> addCart(@RequestBody CartRequest cartRequest){
        CartResponse response = this.cartService.createCart(cartRequest);
        log.info("Add new Item to Cart");
        return ResponseHandler.generateResponse("Success add product to cart", HttpStatus.OK, response);
    }

    @DeleteMapping("/cart/{id}/delete")
    public ResponseEntity<Object> deleteCart(@PathVariable(value = "id") UUID id){
        log.info("Successfully removed cart");
        this.cartService.deleteCart(id);
        return ResponseHandler.generateResponse("Success delete cart with id", HttpStatus.OK, null);
    }

    @PutMapping("/cart/{id}/updateqty")
    public ResponseEntity<Object> changeQtyItemCart(@PathVariable(value = "id") UUID id, UpdateQtyCart updateQtyCart){
        CartResponse response = this.cartService.updateQtyCart(updateQtyCart,id);
        return ResponseHandler.generateResponse("Success change qty of the product", HttpStatus.OK, response);
    }

}