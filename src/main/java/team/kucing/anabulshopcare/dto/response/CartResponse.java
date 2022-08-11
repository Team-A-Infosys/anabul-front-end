package team.kucing.anabulshopcare.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private String productName;

    private String imageProduct;

    private String category;

    private String description;

    private int quantity;

    private double subTotal;

}