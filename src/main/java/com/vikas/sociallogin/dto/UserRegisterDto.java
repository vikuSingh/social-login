package com.vikas.sociallogin.dto;

public class UserRegisterDto {

	private Long userId;
	private String fullName;
	private String email;
	private String password;
	private String mobileNo;

	public UserRegisterDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "UserRequestDto [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", password="
				+ password + ", mobileNo=" + mobileNo + "]";
	}

}
