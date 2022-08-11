package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequest {

    private String couponCode;

    private double usabilityValue;

    private double maxValue;


}