package com.vikas.sociallogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikas.sociallogin.dto.UserRegisterDto;
import com.vikas.sociallogin.model.Provider;
import com.vikas.sociallogin.model.User;
import com.vikas.sociallogin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

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
		System.out.println(username + "service");
		User user = userRepository.findByEmail(username);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException("USER NOT FOUND");
		}
		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).authorities("USER").build();
		return userDetails;
	}

}
