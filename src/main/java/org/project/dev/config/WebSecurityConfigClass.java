package org.project.dev.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigClass {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.formLogin()
                .loginPage("/index/login")
                .loginProcessingUrl("index")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/index") //성곻시
                .failureForwardUrl("/index/login"); //실패시
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/index/logout"))
                .logoutSuccessUrl("/index");

        return http.build();
    }
}
