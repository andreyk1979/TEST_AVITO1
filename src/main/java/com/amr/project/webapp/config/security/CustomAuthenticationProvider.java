package com.amr.project.webapp.config.security;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String verificationCode =
                ((CustomWebAuthenticationDetails) authentication.getDetails()).getVerificationCode();
        User user = userDao.findByUserName(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if ((user == null) || !encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (user.isUsing2FA()) {
            Totp totp = new Totp(verificationCode);
            try {
                if (!totp.verify(verificationCode)) {
                    throw new BadCredentialsException("Invalid verification code");
                }
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid verification code");
            }
        }


        return new UsernamePasswordAuthenticationToken(user, password, Arrays.asList(new SimpleGrantedAuthority(user.getRole().getAuthority())));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
