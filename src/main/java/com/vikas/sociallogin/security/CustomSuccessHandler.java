package com.vikas.sociallogin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vikas.sociallogin.model.Provider;
import com.vikas.sociallogin.model.User;
import com.vikas.sociallogin.repository.UserRepository;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("onAuthenticationSuccess get called");

		String redirectUrl = null;

		if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
			String username = defaultOAuth2User.getAttribute("email") != null ? defaultOAuth2User.getAttribute("email")
					: defaultOAuth2User.getAttribute("login") + "@gmail.com";
			
			System.out.println(defaultOAuth2User.toString() + "========");
			

			if (userRepository.findByEmail(username) == null) {
				User user = new User();
				user.setActive(true);
				user.setEmail(username);
				user.setProvider(Provider.GOOGLE);
				user.setFullName(defaultOAuth2User.getAttribute("email") != null
						? defaultOAuth2User.getAttribute("email").toString().replace("@gmail.com", "")
						: defaultOAuth2User.getAttribute("login"));
				userRepository.save(user);
			}

		}
		redirectUrl = "/dashboard";
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);

	}

}
