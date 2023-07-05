package com.vikas.sociallogin.constant;

public final class Constant {
    private Constant(){}

    public static final String GOOGLE = "[ROLE_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]";
    public static final String GITHUB = "[ROLE_USER, SCOPE_read:user,user:email]";
    public static final String SUCCESS_OTP = "SUCCESS";
    public static final String FAILER_OTP = "FAILER";
}
