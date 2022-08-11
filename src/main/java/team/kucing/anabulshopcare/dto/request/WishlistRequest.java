package team.kucing.anabulshopcare.dto.request;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistRequest {
    private UUID productId;

    private String emailUser;
}