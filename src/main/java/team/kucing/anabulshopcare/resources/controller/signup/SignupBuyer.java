package team.kucing.anabulshopcare.resources.controller.signup;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.service.UserAppService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class SignupBuyer {

    private UserAppService userAppService;

//    @CrossOrigin("http://api.iksgroup.co.id/")
    @GetMapping("/signup/buyer")
    public String showFormSeller(Model model){
        SignupRequest form = new SignupRequest();
        model.addAttribute("form", form);

        return "signup/signup-buyer";
    }

    @PostMapping("/signup/buyer/processing")
    public String addProduct(RedirectAttributes redirectAttributes, @ModelAttribute("form") @Valid SignupRequest signupRequest, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "/signup/signup-seller";
        }
        this.userAppService.signUpBuyer(signupRequest, file);
        redirectAttributes.addFlashAttribute("notification",
                String.format("Contractor \"%s\" successfully saved", signupRequest.getEmail()));
        redirectAttributes.addFlashAttribute("action", "save");
        log.info("Success Register User Buyer " + signupRequest.getEmail());


        return "signup/success-signup";
    }
}