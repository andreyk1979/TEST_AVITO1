package com.amr.project.webapp.config.security.Oauth2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.amr.project.model.entity.AuthenticationProvider;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        String name = oidcUser.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            //register new user
           userService.createNewUserAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);

        } else {
            //update existing user
            userService.updateUserAfterOAuthLoginSuccess(user, name, AuthenticationProvider.GOOGLE);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
