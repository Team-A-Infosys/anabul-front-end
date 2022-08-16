package team.kucing.anabulshopcare.dto.response;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private UUID productId;

    private String productName;

    private String imageUrl;

    private CategoryResponse category;

    private String description;

    private double price;

    private Integer stock;

    private String location;

}