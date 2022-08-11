package team.kucing.anabulshopcare.dto.response;

import lombok.*;
import team.kucing.anabulshopcare.entity.Role;

import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResponse {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Collection<Role> roles;
}