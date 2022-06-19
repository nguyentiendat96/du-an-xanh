package com.doan.project.webadmin.projectAdmin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doan.project.webadmin.projectAdmin.entities.Role;
import com.doan.project.webadmin.projectAdmin.entities.User;
import com.doan.project.webadmin.projectAdmin.repository.UserRepository;
import com.doan.project.webadmin.projectAdmin.service.UserDetailsServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/user/login?logout";
	}
	
	@PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Validated User user,
        BindingResult result) {
		
		System.out.println("registration");
		  User existing = userRepository.findByEmail(user.getEmail());
		  if (existing != null) { 
			  result.rejectValue("email", null, "There is already an account registered with that email"); }
		  
		  if (result.hasErrors()) {
			  System.out.println("error");
			  return "register";
		  }
		  Role role = new Role(1, "ROLE_MEMBER");
		  user.setRole(role);
		  
		  user.setPassword(passwordEncoder.encode(user.getPassword()));
		  userRepository.save(user);
		  
        return "redirect:/user/login";
    }

}
