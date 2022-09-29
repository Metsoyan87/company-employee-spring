package com.example.companyemployeespring.config;

import com.example.companyemployeespring.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .formLogin()
                .and()
                .logout()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/employee/add").permitAll()
                .antMatchers("/company/add").hasAnyAuthority(Position.MANAGER.name())
                .antMatchers("/employee").authenticated()
                .antMatchers("/company").permitAll()
                .anyRequest()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }


}
