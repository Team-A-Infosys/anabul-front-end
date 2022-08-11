package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CouponRequest;
import team.kucing.anabulshopcare.entity.Coupon;

public interface CouponService {
    Coupon createCoupon(CouponRequest request);

    void deleteCoupon(String couponCode);
}