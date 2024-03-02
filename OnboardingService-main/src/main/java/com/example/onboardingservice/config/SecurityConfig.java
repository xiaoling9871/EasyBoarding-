package com.example.onboardingservice.config;

import com.example.onboardingservice.security.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter filter) {
        this.jwtFilter = filter;
    }

    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers(
                        "/onboarding/swagger-ui.html",
                        "/onboarding/",
                        "/onboarding/swagger-ui.html",
                        "/onboarding/swagger-ui/",
                        "/onboarding/*.html",
                        "/onboarding/swagger-ui/favicon*.*",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/swagger-resources/**",
                        "/**/v3/api-docs/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}