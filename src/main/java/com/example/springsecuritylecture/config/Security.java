package com.example.springsecuritylecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class Security {
    private final String realmBasic = "realm basic";

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {

        http
//                .httpBasic(withDefaults())
                .httpBasic(basic ->
                        basic
                                // 인증 실패시 실행
                                // BasicAuthenticationEntryPoint 참고
                                .authenticationEntryPoint(authenticationEntryPoint)
                )
                .authorizeHttpRequests(auth -> {
                    auth
                            .anyRequest()
                            .authenticated();
                });

        return http.build();
    }

    @Bean
    public UserDetailsService inmemoryDetailsService() {
        UserDetails user1 = User.withUsername("user1").password("{noop}1234").roles("USER").build();
        UserDetails user2 = User.withUsername("user2").password("{noop}1234").roles("USER").build();
        UserDetails user3 = User.withUsername("user3").password("{noop}1234").roles("USER").build();

        return new InMemoryUserDetailsManager(Arrays.asList(user1, user2, user3));
    }

    // BasicAuthenticationEntryPoiont 와 동일
    AuthenticationEntryPoint authenticationEntryPoint = (request, response, authException) -> {
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE.toString(), "Basic realm=\"" + realmBasic + "\"");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    };
}
