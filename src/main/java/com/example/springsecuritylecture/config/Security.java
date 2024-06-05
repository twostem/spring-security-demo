package com.example.springsecuritylecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(form ->
                        form
                                .loginProcessingUrl("/loginProc")
                                .defaultSuccessUrl("/")
                                .failureUrl("/failed")
                                .usernameParameter("userId")
                                .passwordParameter("password")
                                .successHandler(((request, response, authentication) -> {
                                    System.out.println("authentication: " + authentication);
                                    response.sendRedirect("/home");
                                }))
                                .failureHandler(((request, response, exception) -> {
                                    System.out.println("exception: " + exception.getMessage());
                                    response.sendRedirect("/login");
                                }))
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService inmemoryDetailsService() {
        UserDetails user1 = User.withUsername("user1").password("{noop}1234").roles("USER").build();
        UserDetails user2 = User.withUsername("user2").password("{noop}1234").roles("USER").build();
        UserDetails user3 = User.withUsername("user3").password("{noop}1234").roles("USER").build();

        return new InMemoryUserDetailsManager(Arrays.asList(user1, user2, user3));
    }
}
