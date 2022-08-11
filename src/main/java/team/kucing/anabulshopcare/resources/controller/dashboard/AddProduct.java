package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.dto.response.ProductResponse;
import team.kucing.anabulshopcare.service.ProductService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class AddProduct {
    private final ProductService productService;

    @GetMapping("/product/add")
    public String addProduct(Model model){
        ProductRequest product = new ProductRequest();
        model.addAttribute("product", product);
        return "dashboard/addproduct";
    }

    @PostMapping("/product")
    public String addProduct(RedirectAttributes redirectAttributes, @ModelAttribute("product") @Valid ProductRequest productRequest, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "dashboard/addproduct";
        }
        this.productService.createProduct(productRequest, file);
        redirectAttributes.addFlashAttribute("notification",
                String.format("Contractor \"%s\" successfully saved", productRequest.getProductName()));
        redirectAttributes.addFlashAttribute("action", "save");
        log.info("Success Add Product " + productRequest.getProductName());


        return "redirect:/dashboard/product/add";
    }
}