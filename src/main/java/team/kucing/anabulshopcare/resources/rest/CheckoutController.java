//package team.kucing.anabulshopcare.resources.rest;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import team.kucing.anabulshopcare.dto.request.CheckoutRequest;
//import team.kucing.anabulshopcare.dto.response.CheckoutResponse;
//import team.kucing.anabulshopcare.handler.ResponseHandler;
//import team.kucing.anabulshopcare.service.CheckoutService;
//
//import java.util.UUID;
//
//@RestController
//@AllArgsConstructor
//@Tag(name = "07. Checkout Controller")
//public class CheckoutController {
//    private CheckoutService checkoutService;
//
//    @PostMapping("/checkout/{id}")
//    public ResponseEntity<Object> checkoutCart(@PathVariable("id") UUID id, @RequestBody CheckoutRequest checkoutRequest) {
//        CheckoutResponse response = this.checkoutService.createCheckout(id, checkoutRequest);
//        return ResponseHandler.generateResponse("Success checkout ", HttpStatus.OK, response);
//    }
//
//    @PostMapping("/checkout/{id}/cancel")
//    public ResponseEntity<Object> cancelCheckout(@PathVariable("id") UUID id){
//        this.checkoutService.cancelCheckout(id);
//        return ResponseHandler.generateResponse("Success delete checkout", HttpStatus.OK, null);
//    }
//
//    @PostMapping("/checkout/{id}/confirmPayment")
//    public ResponseEntity<Object> confirmPayment(@PathVariable("id") UUID id){
//        CheckoutResponse response = this.checkoutService.confirmPayment(id);
//        return ResponseHandler.generateResponse("Success Pay The Checkout", HttpStatus.OK, response);
//    }
//}