package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.response.ProductResponse;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.UserAppDetails;
import team.kucing.anabulshopcare.service.ProductService;
import team.kucing.anabulshopcare.service.UserAppService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class StockProduct {

    private final ProductService productService;

    private final UserAppService userAppService;

    @GetMapping("/product/stock")
//    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String showProduct(Authentication principal, Model model){

        return listProducts(principal,model, 1);
    }

    @GetMapping("/product/stock/{page}")
//    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public String listProducts(Authentication principal, Model model, @PathVariable("page") int page){
        UserResponse userApp = this.userAppService.getUserByEmail(principal.getName());
        model.addAttribute("userApp", userApp);

        int size = 4;

        Page<Product> listProducts = this.productService.listMyProduct(page, size, principal);
        if (listProducts.getTotalElements() == 0){
           model.addAttribute("products", null);
           return "dashboard/stockproduct";
        }

        model.addAttribute("products", listProducts.getContent().stream().map(Product::convertToResponse).toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("totalProduct", listProducts.getTotalElements());
        return "dashboard/stockproduct";
    }

    @GetMapping("/product/search")
    public String searchProductName(@AuthenticationPrincipal UserAppDetails userAppDetails, Model model, @RequestParam(value = "nama", required = false) String nama){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userAppDetails.getUsername()));

        int size = 10;

        Page<Product> listProducts = this.productService.filterProductByNameForSeller(nama, 1, size, userAppDetails.getUsername());
        if (listProducts.getTotalElements() == 0){
            model.addAttribute("products", null);
            return "dashboard/stockproduct";
        }

        if (Objects.equals(nama, "")){
            return "redirect:/dashboard/product/stock";
        }

        model.addAttribute("nama", nama);
        model.addAttribute("products", listProducts.getContent().stream().map(Product::convertToResponse).toList());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", listProducts.getTotalPages());
        model.addAttribute("totalProduct", listProducts.getTotalElements());
        return "dashboard/stockproduct";
    }

    @GetMapping("/product/{id}/publish")
    public String publishProduct(@PathVariable("id") UUID id){
        this.productService.publishedStatus(id);
        return "redirect:/dashboard/product/stock";
    }

    @GetMapping("/product/{id}/archive")
    public String archiveProduct(@PathVariable("id") UUID id){
        this.productService.archivedStatus(id);
        return "redirect:/dashboard/product/stock";
    }

    @GetMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") UUID id){
        this.productService.deleteProduct(id);

        return "redirect:/dashboard/product/stock";
    }
}