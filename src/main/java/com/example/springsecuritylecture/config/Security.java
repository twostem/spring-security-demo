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
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class Security {

    // RequestCache: 인증 절차 문제로 리다이렉트 된 후에 이전에 했던 요청 정보를 담고 있는 'SavedRequest' 객체를 쿠키 혹은 세션에 저장하고
    // 필요시 다시 가져와 실행하는 캐시 메카니즘

    // SavedRequest: 로그인과 같은 인증 절차 후 사용자를 인증 이전의 원래 페이지로 안내하며 이전 요청과 관련된 여려 정보를 저장한다.

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        CookieRequestCache cookieRequestCache = new CookieRequestCache();
        new RequestMatcher
        cookieRequestCache.setRequestMatcher(Req);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logoutSuccess").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .requestCache(requestCache -> requestCache
                        .requestCache(new CookieRequestCache())
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
