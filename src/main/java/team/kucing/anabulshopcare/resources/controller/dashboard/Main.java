package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import team.kucing.anabulshopcare.service.ProductService;

@Controller
@AllArgsConstructor
@Slf4j
public class Main {

    private final ProductService productService;

    @GetMapping("/dashboard")
    public String indexDashboard(){
        return "dashboard/index";
    }

}