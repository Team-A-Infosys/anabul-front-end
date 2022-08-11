package team.kucing.anabulshopcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.kucing.anabulshopcare.entity.Wishlist;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;

    private String productName;

    private boolean isPublished;

    private String description;

    private String category;

    private String firstName;

    private String imageProduct;

    private Integer stock;

    private double price;

    private String location;

    private String wishlistByUser;

    private String cartByUser;

    private String totalBuyer;
}