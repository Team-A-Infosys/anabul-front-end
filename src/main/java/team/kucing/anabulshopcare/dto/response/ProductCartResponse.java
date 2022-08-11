package team.kucing.anabulshopcare.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartResponse {
    private String productName;

    private double price;

}
