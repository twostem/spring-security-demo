package com.example.springsecuritylecture.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logoutSuccess").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logoutProc")
                        // csrf를 disable 하거나 RequestMatcher를 사용하면 logout 실행을 POST 이외의 방법으로 할 수 있음
                        .logoutRequestMatcher(
                                new AntPathRequestMatcher("/logoutProc", "GET")
                        )
                        .logoutSuccessUrl("/logoutSuccess")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/logoutSuccess");
                        })
                        .deleteCookies("JSESSIONID", "remember-me")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .addLogoutHandler(((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.invalidate();
                            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(null);
                            SecurityContextHolder.getContextHolderStrategy().clearContext();
                        }))
                        .permitAll()
                )

        ;

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
