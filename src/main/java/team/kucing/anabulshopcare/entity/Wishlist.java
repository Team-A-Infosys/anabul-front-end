package team.kucing.anabulshopcare.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import team.kucing.anabulshopcare.dto.response.WishlistResponse;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE wishlist SET is_deleted = true WHERE wishlist_id=?")
@Where(clause = "is_deleted = false")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserApp userApp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Product product;

    private boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public WishlistResponse convertToResponse(){
        return WishlistResponse.builder()
                .productName(this.product.getName())
                .description(this.product.getDescription())
                .category(this.product.getCategory().convertResponseCategory())
                .location(this.product.getLocation())
                .price(this.product.getPrice())
                .stock(this.product.getStock())
        .build();
    }
}