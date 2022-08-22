package team.kucing.anabulshopcare.resources.controller.signup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Signup {

    @GetMapping("/signup")
    public String signup(){
        return "signup/signup";
    }
}