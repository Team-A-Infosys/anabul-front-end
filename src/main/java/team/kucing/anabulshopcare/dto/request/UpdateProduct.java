package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProduct {

    private String productName;

    private String description;

    private CategoryRequest category;

    private Integer stock;

    private double price;
}