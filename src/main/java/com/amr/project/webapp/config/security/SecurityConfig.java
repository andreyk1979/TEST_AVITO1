package com.amr.project.webapp.config.security;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amr.project.service.impl.CustomOauth2UserService;

import com.amr.project.webapp.config.security.Oauth2.OAuth2LoginSuccessHandler;


@Configuration
@ComponentScan("com.amr.project")
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService oauth2UserService;

    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private CustomAuthenticationProvider provider;
    private CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource;


    @Autowired
    public void setProvider(CustomAuthenticationProvider provider) {
        this.provider = provider;
    }

    @Autowired
    public void setCustomWebAuthenticationDetailsSource(CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource) {
        this.customWebAuthenticationDetailsSource = customWebAuthenticationDetailsSource;
    }

    @Autowired
    public void setOAuth2LoginSuccessHandler(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    public SecurityConfig(CustomOauth2UserService oauth2UserService) {
        super();
        this.oauth2UserService = oauth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/moderation/**").hasAnyAuthority("MODERATOR", "ADMIN")
                // Модератору временно разрешено просматривать информацию о юзере. Юзеру необходимо создать публичную страницу, которую смогут просматривать все.
                .antMatchers("/user/**", "/chat/**", "/shop/registration/**").hasAnyAuthority("USER", "MODERATOR", "ADMIN")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", true)
                .authenticationDetailsSource(customWebAuthenticationDetailsSource)
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(oauth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .authenticationDetailsSource(customWebAuthenticationDetailsSource)
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}