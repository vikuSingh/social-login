package com.vikas.sociallogin.dto;

public class UserLoginDto {

	private String username;
	private String password;

	public UserLoginDto() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLoginDto [username=" + username + ", password=" + password + "]";
	}

}
