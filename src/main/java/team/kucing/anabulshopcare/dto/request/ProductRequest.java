package team.kucing.anabulshopcare.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;

    private String description;

    private CategoryRequest category;

    private String emailUser;

    private Integer stock;

    private double price;
}