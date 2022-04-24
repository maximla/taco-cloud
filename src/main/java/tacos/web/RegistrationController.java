package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.UserRepository;
import tacos.security.PasswordEncoderHolder;
import tacos.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoderHolder passwordEncoderHolder;

    @Autowired
    public RegistrationController(UserRepository userRepo, PasswordEncoderHolder passwordEncoderHolder) {
        this.userRepo = userRepo;
        this.passwordEncoderHolder = passwordEncoderHolder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepo.save(form.toUser(passwordEncoderHolder.getInstance()));
        return "redirect:/login";
    }
}
