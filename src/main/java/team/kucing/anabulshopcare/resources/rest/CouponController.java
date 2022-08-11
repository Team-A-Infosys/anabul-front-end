package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CouponRequest;
import team.kucing.anabulshopcare.entity.Coupon;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.service.CouponService;

@RestController
@AllArgsConstructor
@Tag(name = "08. Coupon Controller")
public class CouponController {

    private CouponService couponService;

    @PostMapping("/coupon/create")
    public ResponseEntity<Object> createCoupon(@RequestBody CouponRequest request) {
        Coupon response = this.couponService.createCoupon(request);
        return ResponseHandler.generateResponse("Success create Coupon", HttpStatus.CREATED, response);
    }

    @DeleteMapping("/coupon/delete/{couponCode}")
    public ResponseEntity<Object> deleteCoupon(@PathVariable(value = "couponCode") String couponCode) {
        this.couponService.deleteCoupon(couponCode);
        return ResponseHandler.generateResponse("Success delete Coupon", HttpStatus.OK, null);
    }
}