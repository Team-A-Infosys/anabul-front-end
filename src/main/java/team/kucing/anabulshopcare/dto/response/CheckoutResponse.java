package team.kucing.anabulshopcare.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {

    private List<CartResponse> cart;

    private String shipmentAddress;

    private String courier;

    private double shipmentCost;

    private String couponCode;

    private String discount;

    private double checkoutTotal;

    private boolean isPaid;

    private PaymentResponse paymentGateway;
}