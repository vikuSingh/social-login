package com.vikas.sociallogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vikas.sociallogin.dto.UserRegisterDto;
import com.vikas.sociallogin.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String registrationPage() {
		return "registration";
	}

	@PostMapping
	public String save(@ModelAttribute UserRegisterDto userRegisterDto) {
		if (userService.registerUser(userRegisterDto) != null) {
			return "login";
		}
		return "registration";
	}

}
