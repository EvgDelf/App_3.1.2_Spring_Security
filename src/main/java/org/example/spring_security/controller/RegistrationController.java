package org.example.spring_security.controller;

import org.example.spring_security.model.User;
import org.example.spring_security.repository.UserRepository;
import org.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(@RequestParam("login") String login,@RequestParam("password") String password,
                                          @RequestParam("name") String firstName, @RequestParam("lastName") String lastName,
                                          @RequestParam("age") int age, @RequestParam("email") String email) {
        userService.registerUser(login,password,firstName,lastName,age,email);
        return "redirect:/login";
    }

}
