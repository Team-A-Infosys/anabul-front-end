package team.kucing.anabulshopcare.entity;
import lombok.*;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.BuyerResponse;
import team.kucing.anabulshopcare.dto.response.SellerResponse;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.entity.image.ImageProduct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user_app SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Entity
public class UserApp extends ImageProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    private String imageUrl;

    private String password;

    @ManyToMany(fetch = EAGER, cascade = CascadeType.PERSIST)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany
    private List<Wishlist> wishlist = new ArrayList<>();

    @OneToMany
    private List<Cart> cart = new ArrayList<>();

    @OneToMany
    private List<Checkout> history = new ArrayList<>();

    private boolean isDeleted = FALSE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public UserResponse convertToResponse(){
        return UserResponse.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .address(this.address.getProvinsi().getNama()+", " +
                        this.address.getKota().getNama()+", " +
                        this.address.getKecamatan().getNama()+", " +
                        this.address.getKelurahan().getNama())
                .history(this.history.stream().map(Checkout::convertToResponse).toList())
                .wishlistProduct(this.wishlist.stream().map(Wishlist::convertToResponse).collect(Collectors.toList()))
                .cartList(this.cart.stream().filter(cart1 -> cart1.isCheckout()==FALSE).map(Cart::convertToResponse).toList())
                .roles(this.roles).build();
    }

    public SellerResponse convertToSellerResponse(){
        return SellerResponse.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .address(this.address.getProvinsi().getNama()+", " +
                        this.address.getKota().getNama()+", " +
                        this.address.getKecamatan().getNama()+", " +
                        this.address.getKelurahan().getNama())
                .roles(this.roles).build();
    }

    public BuyerResponse convertToBuyerResponse(){
        return BuyerResponse.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .address(this.address.getProvinsi().getNama()+", " +
                        this.address.getKota().getNama()+", " +
                        this.address.getKecamatan().getNama()+", " +
                        this.address.getKelurahan().getNama())
                .history(this.history.stream().filter(checkout -> checkout.isPaid()==TRUE).map(Checkout::convertToResponse).toList())
                .wishlistProduct(this.wishlist.stream().map(Wishlist::convertToResponse).collect(Collectors.toList()))
                .cartList(this.cart.stream().filter(cart1 -> cart1.isCheckout()==FALSE).map(Cart::convertToResponse).toList())
                .roles(this.roles).build();
    }
}