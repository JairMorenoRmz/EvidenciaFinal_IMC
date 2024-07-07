package com.example.bmicalculator.controller;

import com.example.bmicalculator.entity.User;
import com.example.bmicalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (user.getHeight() < 1.0 || user.getHeight() > 2.5 || user.getAge() < 15) {
            model.addAttribute("error", "Invalid height or age");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username).orElseThrow();
        model.addAttribute("user", user);
        return "profile";
    }
}

