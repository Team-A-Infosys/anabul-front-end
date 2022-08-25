package team.kucing.anabulshopcare.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {

    private UUID id;

    private List<CartResponse> cart;

    private String shipmentAddress;

    private String courier;

    private double shipmentCost;

    private String couponCode;

    private String discount;

    private double checkoutTotal;

    private boolean isPaid;

    private PaymentResponse paymentGateway;

    private Date createdAt;
}