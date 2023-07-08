package com.vikas.sociallogin.service;

import com.vikas.sociallogin.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.US);

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
            javaMailSender.send(optEmailSend(user, otp));
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private SimpleMailMessage optEmailSend(User user, int otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(user.getEmail());

        msg.setSubject("Log in to your account");
        msg.setText("Please enter the following verification code to verify this login attempt." + "\n\n" + otp + "\n\n" + "Regards \n" + "Vikas Kumar");
        return msg;
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

    @Override
    public String sendResetEmail(String email, HttpServletRequest request) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("USER NOT FOUND");
            }
            String token = UUID.randomUUID().toString();
            user.setEmail(email);
            user.setPasswordToken(token);
            userRepository.save(user);
            javaMailSender.send(constructEmail(getAppUrl(request), request.getLocale(), token, user));
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private SimpleMailMessage constructEmail(String appUrl, Locale locale, String token, User user) {
        String url = appUrl + "/forgot/changePwd?token=" + token;
        String message = resourceBundle.getString("message.resetPassword");
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(fromEmail);
        return email;
    }

    @Override
    public String updatePassword(UserLoginDto userLoginDto) {
        User user = userRepository.findByPasswordToken(userLoginDto.getPasswordToken());
        if (user != null) {
            user.setPassword(passwordEncoder.encode(userLoginDto.getPassword()));
            userRepository.save(user);
            return "SUCCESS";
        }
        return null;
    }

}
