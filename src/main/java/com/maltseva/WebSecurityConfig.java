package com.maltseva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/static/*/*.*", "/user/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/user/login")
                    .failureUrl("/user/login?error")
                    .defaultSuccessUrl("/")
                    .usernameParameter("login").passwordParameter("password")
                    .and()
                    .logout().logoutSuccessUrl("/user/login?logout")
                    .and()
                    .exceptionHandling().accessDeniedPage("/user/register")
                    .and()
                    .csrf();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}