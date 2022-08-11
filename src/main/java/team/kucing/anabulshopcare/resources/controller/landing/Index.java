package team.kucing.anabulshopcare.resources.controller.landing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.service.ProductService;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class Index {

    private ProductService productService;

    @GetMapping("/")
    public String index(Model model){
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
    public String product(Model model){
        return listProducts(model, 1);
    }

    @GetMapping("/product/{page}")
    public String listProducts(Model model, @PathVariable("page") int page){
        int size = 20;

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
    @GetMapping("/product-detail/{id}")
    public String getProduct(@PathVariable("id") UUID id, Model model){
        model.addAttribute("product", this.productService.getProduct(id));

        return "index/product-detail";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(){
        return "index/shopping-cart";
    }

}