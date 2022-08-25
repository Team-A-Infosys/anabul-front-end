package team.kucing.anabulshopcare.dto.response;

import lombok.*;

import java.util.Date;

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

    private double price;

    private int quantity;

    private double subTotal;

    private Date createdAt;

}