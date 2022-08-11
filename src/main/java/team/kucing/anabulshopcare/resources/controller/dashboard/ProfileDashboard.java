package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.kucing.anabulshopcare.service.ProductService;

@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class ProfileDashboard {
    private final ProductService productService;

    @GetMapping("/toko-kamu")
    public String calendarDashboard(){
        return "utils/fitur-coming-soon";
    }
}