package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.CheckoutResponse;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE checkout SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Checkout {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToMany
    private List<Cart> cart = new ArrayList<>();

    @ManyToOne
    private UserApp userApp;

    private double checkoutTotal;

    private String couponCode;

    private String valueCoupon;

    private String shipmentAddress;

    @OneToOne
    private Payment paymentGateway;

    private double shipmentCost;

    private String courier;

    private boolean isDeleted = Boolean.FALSE;

    private boolean isPaid = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public CheckoutResponse convertToResponse(){
        return CheckoutResponse.builder()
                .cart(this.cart.stream().map(Cart::convertToResponse).collect(Collectors.toList()))
                .shipmentAddress(this.shipmentAddress)
                .couponCode(this.couponCode)
                .shipmentCost(this.shipmentCost)
                .courier(this.courier)
                .isPaid(this.isPaid)
                .paymentGateway(this.paymentGateway.convertToResponse())
                .discount(this.valueCoupon)
                .checkoutTotal(this.checkoutTotal).build();
    }
}