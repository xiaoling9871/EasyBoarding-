package com.example.s3service.config;

import com.example.s3service.security.JwtFilter;
import com.example.s3service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }


    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers("/s3/",
                        "/s3/swagger-ui.html",
                        "/s3/swagger-ui/",
                        "/s3/*.html",
                        "/s3/swagger-ui/favicon*.*",
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
                .addFilterAfter(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }
}