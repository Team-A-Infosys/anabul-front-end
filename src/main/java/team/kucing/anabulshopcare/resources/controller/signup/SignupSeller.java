package team.kucing.anabulshopcare.resources.controller.signup;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.UserAppService;
import team.kucing.anabulshopcare.service.impl.AddressServiceImpl;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class SignupSeller {

    private UserAppService userAppService;

    private AddressServiceImpl addressService;

    @CrossOrigin("http://api.iksgroup.co.id/")
    @GetMapping("/signup/seller")
    public String showFormSeller(Model model){
        SignupRequest form = new SignupRequest();
        model.addAttribute("form", form);

        return "signup/signup-seller";
    }

    @PostMapping("/signup/seller/processing")
    public String addProduct(RedirectAttributes redirectAttributes, @ModelAttribute("form") @Valid SignupRequest signupRequest, BindingResult result, @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "/signup/signup-seller";
        }
        this.userAppService.signUpSeller(signupRequest, file);
        redirectAttributes.addFlashAttribute("notification",
                String.format("Contractor \"%s\" successfully saved", signupRequest.getEmail()));
        redirectAttributes.addFlashAttribute("action", "save");
        log.info("Success Register User Seller " + signupRequest.getEmail());


        return "redirect:/";
    }
}