package com.example.springsecuritylecture.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/anonymous")
    public String anonymous() {
        return "anonymous";
    }

    //    컨트롤러가 실행되는 시점이면 이미 모든 Filter를 거쳤으니 AnonymousAuthenticationFilter도 거쳤을거라 생각하고, SecurityContext에도 AnonymousAthenticationToken이 있으니,
    //    Authentication에 AnonymousAthenticationToken이 있을거라 생각했는데, 왜 null이 있는건가요??
    //
    //    그리고 왜 @CurrentSecurityContext로 찾을때만 AnonymousAthenticationToken를 받을수 있는건가요??
    @GetMapping("/authentication")
    public String authentication(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        } else {
            return "authentication";
        }
    }

    @GetMapping("/authenticationContext")
    public String authenticationContext(@CurrentSecurityContext SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        } else {
            return "authenticationContext";
        }
    }
}
