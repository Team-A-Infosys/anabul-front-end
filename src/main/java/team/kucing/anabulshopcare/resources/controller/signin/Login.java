package team.kucing.anabulshopcare.resources.controller.signin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class Login {

    @GetMapping("/login")
    public String userLogin(){
        return "login/login";
    }
}