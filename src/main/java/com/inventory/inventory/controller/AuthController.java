package com.inventory.inventory.controller;

import com.inventory.inventory.model.User;
import com.inventory.inventory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) throws IOException {

        userService.register(user);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) throws IOException {

        if (userService.login(user.getUsername(), user.getPassword())) {

            session.setAttribute("user", user.getUsername());

            return "redirect:/dashboard";
        }

        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}