package com.amr.project.webapp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
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

    public SecurityConfig() {
        super();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/").hasRole("USER")
                .anyRequest().authenticated()
                .and().formLogin()
                .authenticationDetailsSource(customWebAuthenticationDetailsSource)
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }
}