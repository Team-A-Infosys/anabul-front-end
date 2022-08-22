package team.kucing.anabulshopcare.resources.controller.handling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Handling {

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "error/404";
    }
}