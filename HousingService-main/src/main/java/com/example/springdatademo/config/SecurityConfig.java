package com.example.springdatademo.config;
import com.example.contentapp.security.JwtFilter;
import com.example.springdatademo.security.JwtProvider;
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
                .antMatchers("/housing/actuator/**")
                .antMatchers("/housing/swagger**/**")
                .antMatchers("/housing/webjars/**")
                .antMatchers("/housing/v2/**")
                .antMatchers("/housing/doc.html");
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }
}