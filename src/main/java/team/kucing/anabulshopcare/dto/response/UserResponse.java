package team.kucing.anabulshopcare.dto.response;

import lombok.*;
import team.kucing.anabulshopcare.entity.Cart;
import team.kucing.anabulshopcare.entity.Checkout;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.Wishlist;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private List<CheckoutResponse> history;

    private List<WishlistResponse> wishlistProduct;

    private List<CartResponse> cartList;

    private Collection<Role> roles;

}