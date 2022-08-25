package team.kucing.anabulshopcare.resources.controller.landing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.kucing.anabulshopcare.dto.request.*;
import team.kucing.anabulshopcare.dto.response.CartResponse;
import team.kucing.anabulshopcare.dto.response.CheckoutResponse;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.entity.*;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.CheckoutRepository;
import team.kucing.anabulshopcare.repository.CouponRepository;
import team.kucing.anabulshopcare.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class Index {

    private ProductService productService;

    private WishlistService wishlistService;

    private UserAppService userAppService;

    private CartService cartService;

    private ShipmentService shipmentService;

    private CouponRepository couponRepository;

    private CheckoutService checkoutService;

    private PaymentService paymentService;

    private CheckoutRepository checkoutRepository;
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserAppDetails userDetails){
        if (userDetails == null){
            model.addAttribute("userApp", null);
        } else {
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));
        }
        return indexListProducts(model, 1);
    }

    @GetMapping("/page/{page}")
    public String indexListProducts(Model model, @PathVariable("page") int page){
        int size = 30;

        Page<Product> listProducts = this.productService.listProducts(page, size);
        if (listProducts.getTotalElements() == 0){
            model.addAttribute("products", null);
            return "index/product";
        }

        model.addAttribute("products", listProducts.getContent().stream().map(Product::convertToResponse).toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("totalProduct", listProducts.getTotalElements());
        return "index/main";
    }

    @GetMapping("/product")
    public String product(Model model, @AuthenticationPrincipal UserAppDetails userDetails){
        return listProducts(model, 1, userDetails);
    }

    @GetMapping("/product/{page}")
    public String listProducts(Model model, @PathVariable("page") int page, @AuthenticationPrincipal UserAppDetails userDetails){
        if (userDetails == null){
            model.addAttribute("userApp", null);
        } else {
            model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));
        }

        int size = 5;

        Page<Product> listProducts = this.productService.listProducts(page, size);
        if (listProducts.getTotalElements() == 0){
            model.addAttribute("products", null);
            return "index/product";
        }

        model.addAttribute("products", listProducts.getContent().stream().map(Product::convertToResponse).toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("totalProduct", listProducts.getTotalElements());
        return "index/product";
    }

    @GetMapping("/product/search")
    public String searchProductName(Model model, @RequestParam(value = "nama", required = false) String nama, @AuthenticationPrincipal UserAppDetails userDetails){
        if (userDetails == null){
            model.addAttribute("userApp", null);
        } else {
            model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));
        }

        int size = 10;

        Page<Product> listProducts = this.productService.filterProductByName(nama, 1, size);
        if (listProducts.getTotalElements() == 0){
            model.addAttribute("products", null);
            return "index/product";
        }

        if (Objects.equals(nama, "")){
            return "redirect:/product";
        }

        model.addAttribute("nama", nama);
        model.addAttribute("products", listProducts.getContent().stream().map(Product::convertToResponse).toList());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("totalProduct", listProducts.getTotalElements());
        return "index/product";
    }
    @GetMapping("/product-detail/{id}")
    public String getProduct(@PathVariable("id") UUID id, Model model, @AuthenticationPrincipal UserAppDetails userDetails){
        if (userDetails == null){
            model.addAttribute("userApp", null);
        } else {
            model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));
        }

        model.addAttribute("product", this.productService.getProduct(id));

        return "index/product-detail";
    }

    @GetMapping("/add-wishlist/{id}")
    public String addWishlist(@PathVariable("id") UUID id,@AuthenticationPrincipal UserAppDetails userDetails){

        String email = userDetails.getUsername();

        this.wishlistService.createWishlist(id, email);

        return "redirect:/product-detail/{id}";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal UserAppDetails userDetails, Model model){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));

        return "profile/user";
    }

    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable("id") UUID id, Model model, @AuthenticationPrincipal UserAppDetails userDetails){
        UserApp userApp = this.userAppService.getUser(id);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName(userApp.getFirstName());
        updateUserRequest.setLastName(userApp.getLastName());
        updateUserRequest.setPhoneNumber(userApp.getPhoneNumber());
        updateUserRequest.setEmail(userApp.getEmail());

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setProvinsi(userApp.getAddress().getProvinsi());
        addressRequest.setKota(userApp.getAddress().getKota());
        addressRequest.setKecamatan(userApp.getAddress().getKecamatan());
        addressRequest.setKelurahan(userApp.getAddress().getKelurahan());

        updateUserRequest.setAddress(addressRequest);

        model.addAttribute("userId", userApp.getId());
        model.addAttribute("userEdit",updateUserRequest);
        model.addAttribute("userApp",this.userAppService.getUserByEmail(userDetails.getUsername()));

        return "profile/edit-profile";
    }

    @PostMapping("/profile/edit/{id}/processing")
    public String editProfileProcessing(@PathVariable("id")UUID id, @ModelAttribute("userEdit") UpdateUserRequest updateUserRequest, @RequestParam("file")MultipartFile file, @AuthenticationPrincipal UserAppDetails userDetails){

        this.userAppService.updateUser( updateUserRequest, file, id);

        return "redirect:/logout";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(@AuthenticationPrincipal UserAppDetails userDetails, Model model){
        UserResponse response = this.userAppService.getUserByEmail(userDetails.getUsername());
        UserApp userApp = this.userAppService.getUser(response.getUserId());

        Shipment shipment = this.shipmentService.findShipmentPriceByProvinsi(userApp.getAddress().getProvinsi());
        double priceShipment = shipment.getPrice();

        var subTotal = response.getCartList().stream().mapToDouble(CartResponse::getSubTotal).sum();
        var total = priceShipment + subTotal;

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        model.addAttribute("checkoutRequest", checkoutRequest);
        model.addAttribute("payment", this.paymentService.getAllPayment());

        model.addAttribute("subTotal", subTotal);
        model.addAttribute("total", total);
        model.addAttribute("priceShipment",priceShipment);
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));

        return "index/shopping-cart";
    }

    @GetMapping("/check/coupon")
    public String checkCoupon(Model model, @RequestParam(value = "coupon", required = false) String coupon, @AuthenticationPrincipal UserAppDetails userDetails, RedirectAttributes redirectAttributes){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));

        Coupon checkCoupon = this.couponRepository.findByCode(coupon.toUpperCase());
        if (checkCoupon != null){
            redirectAttributes.addFlashAttribute("available", "Coupon is Available");
        } else{
            redirectAttributes.addFlashAttribute("notAvailable", "Coupon is not valid");
        }

        return "redirect:/shopping-cart";
    }

    @PostMapping("/checkout")
    public String checkout(Authentication userDetails,Model model, @ModelAttribute("checkoutRequest") CheckoutRequest checkoutRequest){
        UserResponse response = this.userAppService.getUserByEmail(userDetails.getName());
        UserApp userApp = this.userAppService.getUser(response.getUserId());

        this.checkoutService.createCheckout(userApp.getId(), checkoutRequest);
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getName()));

        return "index/success-checkout";
    }

    @GetMapping("/checkout/list")
    public String listCheckout(Authentication userDetails, Model model){
        UserResponse response = this.userAppService.getUserByEmail(userDetails.getName());
        UserApp userApp = this.userAppService.getUser(response.getUserId());

        List<Checkout> checkout = this.checkoutRepository.findByUserAppAndIsPaid(userApp, Boolean.FALSE);

        model.addAttribute("userApp", response);
        model.addAttribute("listCheckout", checkout);
        return "index/confirm-payment";
    }

    @GetMapping("/checkout/details/{id}")
    public String checkoutDetails(@PathVariable("id") UUID id, Authentication userDetails, Model model){
        UserResponse response = this.userAppService.getUserByEmail(userDetails.getName());
        model.addAttribute("userApp", response);

        Optional<Checkout> checkout = this.checkoutRepository.findById(id);
        if (checkout.isPresent()){
            CheckoutResponse getCheckout = checkout.get().convertToResponse();
            model.addAttribute("checkout", getCheckout);
        }

        return "index/checkout-details";
    }

    @GetMapping("/checkout/confirmPayment/{id}")
    public String confirmPayment(@PathVariable("id") UUID id, Authentication userDetails, Model model){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getName()));

        this.checkoutService.confirmPayment(id);

        return "index/success-paid";
    }

    @GetMapping("/add-cart/{id}")
    public String addWishlist(@PathVariable("id") UUID id, @RequestParam("quantity") int quantity, @AuthenticationPrincipal UserAppDetails userDetails){
        String email = userDetails.getUsername();

        CartRequest cartRequest = new CartRequest();
        if (quantity < 1){
            throw new BadRequestException("Quantity must be greater than 0");
        }
        cartRequest.setEmailUser(email);
        cartRequest.setProductId(id);
        cartRequest.setQuantity(quantity);

        this.cartService.createCart(cartRequest);

        return "redirect:/product-detail/{id}";
    }
    
}