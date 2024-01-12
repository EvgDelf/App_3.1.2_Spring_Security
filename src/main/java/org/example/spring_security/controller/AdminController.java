package org.example.spring_security.controller;


import org.example.spring_security.model.User;
import org.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String findUserById (@RequestParam ("id") Long id) {
        User user = userService.findById(id);
        userService.findById(id);
        return "/user" + user.getId();
    }

    @PostMapping("/user-delete")
    public String deleteUser(@RequestParam("id") Long id){
        userService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/addRole")
    public String addRole(@RequestParam("id") Long id) {
        userService.addRole(id);
        return "redirect:/users";
    }

    @PostMapping("/deleteRole")
    public String deleteRole(@RequestParam("id") Long id) {
        userService.deleteRole(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam ("id") Long id, @RequestParam("user") User user) {
        userService.edit(id, user);
        return "redirect:/users";
    }


}
