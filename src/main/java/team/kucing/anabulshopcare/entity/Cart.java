package team.kucing.anabulshopcare.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.CartResponse;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cart SET is_deleted = true WHERE cart_id=?")
//@Where(clause = "is_deleted = false")
public class Cart {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID cartId;

    @ManyToOne
    private UserApp userApp;

    @ManyToOne
    @JsonIgnore
    private Product product;

    private int quantity;

    private double subTotal;

    private boolean isDeleted = Boolean.FALSE;

    private boolean isCheckout = Boolean.FALSE;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public CartResponse convertToResponse(){
        return CartResponse.builder()
                .productName(this.product.getName())
                .imageProduct(this.product.getImageUrl())
                .description(this.product.getDescription())
                .category(this.product.getCategory().getCategoryName())
                .quantity(this.quantity)
                .subTotal(this.subTotal).build();
    }
}