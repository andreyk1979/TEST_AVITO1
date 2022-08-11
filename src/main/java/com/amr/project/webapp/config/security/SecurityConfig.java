package com.amr.project.webapp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@ComponentScan("com.amr.project")
@EnableWebSecurity
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
                .authorizeRequests()
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