package com.vikas.sociallogin.dto;

public class UserLoginDto {

	private String username;
	private String password;

	private int otp;

	private String passwordToken;

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


	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	@Override
	public String toString() {
		return "UserLoginDto{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", otp=" + otp +
				", passwordToken='" + passwordToken + '\'' +
				'}';
	}
}
