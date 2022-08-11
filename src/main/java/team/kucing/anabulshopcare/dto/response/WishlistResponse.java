package team.kucing.anabulshopcare.dto.response;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {

    private String productName;

    private CategoryResponse category;

    private String description;

    private double price;

    private Integer stock;

    private String location;

}