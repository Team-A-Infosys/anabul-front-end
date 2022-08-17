package team.kucing.anabulshopcare.resources.controller.landing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.UserAppDetails;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.service.CartService;
import team.kucing.anabulshopcare.service.ProductService;
import team.kucing.anabulshopcare.service.UserAppService;
import team.kucing.anabulshopcare.service.WishlistService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class Index {

    private ProductService productService;

    private WishlistService wishlistService;

    private UserAppService userAppService;

    private CartService cartService;

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

    @GetMapping("/shopping-cart")
    public String shoppingCart(@AuthenticationPrincipal UserAppDetails userDetails, Model model){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userDetails.getUsername()));

        return "index/shopping-cart";
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