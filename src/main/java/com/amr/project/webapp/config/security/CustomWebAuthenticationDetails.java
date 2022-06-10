package com.amr.project.webapp.config.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private String verificationCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        verificationCode = request.getParameter("activationCode");
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
