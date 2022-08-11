package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CouponRequest;
import team.kucing.anabulshopcare.entity.Coupon;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.CouponRepository;
import team.kucing.anabulshopcare.service.CouponService;

import java.util.Locale;

@Service
@AllArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {
    private CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(CouponRequest request){
        Coupon newCoupon = new Coupon();

        if (this.couponRepository.existsByCode(request.getCouponCode().toUpperCase())) {
            throw new BadRequestException("Coupon code Already Exists");
        }

        if (request.getMaxValue() < 1000) {
            throw new BadRequestException("Value of Coupon at least 1000");
        }

        newCoupon.setCode(request.getCouponCode().toUpperCase());
        newCoupon.setTotalValue(request.getMaxValue());
        newCoupon.setUseValue(request.getUsabilityValue());
        this.couponRepository.save(newCoupon);
        log.info("Coupon saved successfully " + newCoupon);
        return newCoupon;
    }

    @Override
    public void deleteCoupon(String couponCode) {
        Coupon coupon = this.couponRepository.findByCode(couponCode.toUpperCase());

        if (coupon == null) {
            throw new ResourceNotFoundException("Coupon not found");
        }
        this.couponRepository.delete(coupon);
        log.info("Coupon deleted successfully " + coupon);
    }
}