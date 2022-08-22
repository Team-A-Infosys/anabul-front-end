package team.kucing.anabulshopcare.resources.controller.dashboard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.kucing.anabulshopcare.entity.UserAppDetails;
import team.kucing.anabulshopcare.service.ProductService;
import team.kucing.anabulshopcare.service.UserAppService;

@Controller
@AllArgsConstructor
@RequestMapping("/dashboard")
@Slf4j
public class ProfileDashboard {
    private final UserAppService userAppService;

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/toko-kamu")
    public String tokoDashboard(@AuthenticationPrincipal UserAppDetails userAppDetails, Model model){
        model.addAttribute("userApp", this.userAppService.getUserByEmail(userAppDetails.getUsername()));
        return "dashboard/profile";
    }
}