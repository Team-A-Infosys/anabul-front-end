package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team.kucing.anabulshopcare.entity.UserAppDetails;
import team.kucing.anabulshopcare.service.ProductService;
import team.kucing.anabulshopcare.service.UserAppService;

@Controller
@AllArgsConstructor
@Slf4j
public class Main {

    private final UserAppService userAppService;
    
    @GetMapping("/dashboard")
    public String indexDashboard(@AuthenticationPrincipal UserAppDetails userAppDetails, Model model){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userAppDetails.getUsername()));
        return "dashboard/index";
    }

}