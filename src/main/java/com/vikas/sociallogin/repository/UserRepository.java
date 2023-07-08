package com.vikas.sociallogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikas.sociallogin.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String username);

	public User findByPasswordToken(String passwordToken);

}
