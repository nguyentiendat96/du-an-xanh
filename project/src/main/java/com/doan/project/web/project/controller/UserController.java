package com.doan.project.web.project.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doan.project.web.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doan.project.web.project.entities.Role;
import com.doan.project.web.project.entities.User;
import com.doan.project.web.project.repository.UserRepository;
import com.doan.project.web.project.service.UserDetailsServiceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/403")
    public String accessDenied(Model model) {
        return "403";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String loginRegister(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";

    }

    @GetMapping("/conect")
    public String loadConect(Model model) {
        return "conect";

    }

    @GetMapping("/experience")
    public String loadExperience(Model model) {
        return "experience";

    }

    @GetMapping("/information")
    public String loadInformation(Model model) {
        return "information";

    }

    @GetMapping("/net")
    public String loadNet(Model model) {
        return "net";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/user/login?logout";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") User user, BindingResult result) {
        System.out.println("registration");
        User existing = userRepository.findByEmail(user.getEmail());
        if (existing != null) {
            result.addError(new ObjectError("globalError" , "There is already an account registered with that email"));
        }
        if (result.hasErrors()) {
            return "register";
        }
        Role role = roleRepository.findByNameRole("ROLE_MEMBER");
        if (Objects.isNull(role)) {
            role = new Role("ROLE_MEMBER");
        }
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/user/login";
    }

}
