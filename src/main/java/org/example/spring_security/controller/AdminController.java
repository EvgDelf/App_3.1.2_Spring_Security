package org.example.spring_security.controller;


import org.example.spring_security.model.User;
import org.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String showUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @GetMapping("/user/{id}/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PostMapping("/user/{id}/edit")
    public String processEditForm(@RequestParam("id") Long id, @ModelAttribute("user")User user) {
        userService.edit(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/{id}/add-role")
    public String addRoleToUser(@RequestParam("id") Long id) {
        userService.addRole(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/{id}/remove-role")
    public String removeRoleFromUser(@RequestParam("id") Long id) {
        userService.deleteRole(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/user/{id}/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

}
