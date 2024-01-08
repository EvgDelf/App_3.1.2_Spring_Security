package org.example.spring_security.controller;

import org.example.spring_security.model.User;
import org.example.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginPageController {

    private final UserRepository userRepository;

    @Autowired
    public LoginPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String loginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String processLoginForm(@RequestParam("username") String login,
                                   @RequestParam("password") String password,
                                   Model model) {
        User user = userRepository.findByLogin(login);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        model.addAttribute("user", user);
        return "user";
    }
}
