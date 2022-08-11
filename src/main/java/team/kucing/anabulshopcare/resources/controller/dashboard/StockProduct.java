package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.response.ProductResponse;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.service.ProductService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class StockProduct {

    private final ProductService productService;

    @GetMapping("/product/stock")
    public String showProduct(Model model){
        return listProducts(model, 1);
    }

    @GetMapping("/product/stock/{page}")
    public String listProducts(Model model, @PathVariable("page") int page){

        int size = 4;

        Page<Product> listProducts = this.productService.listProducts(page, size);
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
    public String searchProductName(Model model, @RequestParam(value = "nama", required = false) String nama){
        int size = 10;

        Page<Product> listProducts = this.productService.filterProductByName(nama, 1, size);
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