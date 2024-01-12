package org.example.spring_security.controller;

import org.example.spring_security.model.User;
import org.example.spring_security.repository.RoleRepository;
import org.example.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class LoginPageController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public LoginPageController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String loginForm() {
        return "login";
    }


    @GetMapping("/login")
    public String processLogin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        User user = userRepository.findByLogin(username);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getAuthorities().contains(roleRepository.findByName("ADMIN"))) {
                return "users";
            } else if (user.getAuthorities().contains(roleRepository.findByName("USER"))) {
                return "user" + user.getId();
            }
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
