package com.vikas.sociallogin.service;

import com.vikas.sociallogin.constant.Constant;
import com.vikas.sociallogin.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikas.sociallogin.dto.UserRegisterDto;
import com.vikas.sociallogin.model.Provider;
import com.vikas.sociallogin.model.User;
import com.vikas.sociallogin.repository.UserRepository;

import java.util.ConcurrentModificationException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setFullName(userRegisterDto.getFullName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setMobileNo(userRegisterDto.getMobileNo());
        user.setActive(false);
        user.setProvider(Provider.LOCAL);
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).authorities("USER").build();
        return userDetails;
    }

    @Override
    public String generateOtp(User user) {
        try {
            int otp = (int) (Math.random() * 9000) + 1000;
            user.setOtp(otp);
            userRepository.save(user);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("vikash.bitcse@gmail.com");
            msg.setTo(user.getEmail());

            msg.setSubject("Log in to your account");
            msg.setText("Please enter the following verification code to verify this login attempt." + "\n\n" + otp + "\n\n" + "Regards \n" + "ABC");
            //javaMailSender.send(msg);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public User saveOtpDetails(UserLoginDto userLoginDto) {
        User users = userRepository.findByEmail(userLoginDto.getUsername());
        if (users.getOtp() == userLoginDto.getOtp()) {
            users.setActive(true);
            userRepository.save(users);
            return users;
        }
        return null;
    }

}
