package com.vikas.sociallogin.controller;

import com.vikas.sociallogin.dto.UserLoginDto;
import com.vikas.sociallogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/forgot")
public class ForgotPasswordController {

     @Autowired
     private UserService userService;

     @GetMapping
     public String forgotPassword(){
          return "forgotpwd";
     }

     @PostMapping("/sendemail")
     public String getUsername(@ModelAttribute("username") String email, HttpServletRequest request, Model model) {
          System.out.println("sendemail controller" + email);
          String result = userService.sendResetEmail(email, request);
          if(result != null) {
               model.addAttribute("message", "We have sent a reset password link to your email. Please check ?");
               return "resetemail";
          }else {
               return "redirect:?error";
          }
     }
     @GetMapping("/changePwd")
     public String changePwd(@ModelAttribute("token") String token, Model model){
          model.addAttribute("token", token);
          return "changepwd";
     }

     @PostMapping("/resetpwd")
     public String resetPassword(@ModelAttribute UserLoginDto userLoginDto, Model model) {
          System.out.println("Controller" + userLoginDto);
          String result = userService.updatePassword(userLoginDto);
          if(result != null) {
               model.addAttribute("message", "Your password has been reset successfully !");
               return "login";
          }
          return "redirect:changePwd?error";
     }
}
