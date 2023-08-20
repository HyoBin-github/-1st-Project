package org.project.dev.config;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigClass {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // 사용자 요청에 대한 페이지별 설정
        http.authorizeHttpRequests()
                // 모든 사용자 접근 허용
                .antMatchers("/", "/member/login", "/member/join").permitAll()
                // css, images, js
                .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
                // 로그인한 경우, 권한과 상관없이
                .antMatchers("/member/logout", "/member/detail/**", "/member/update/**", "/member/delete/**", "/board/**").authenticated()
                // ADMIN
                .antMatchers("/admin/**", "/member/memberList").hasAnyRole("ADMIN")
                // SELLER, ADMIN
                .antMatchers("/shop/manage").hasAnyRole("SELLER", "ADMIN");
        // MEMBER, ADMIN
//                .antMatchers("/member/detail/**", "/member/update/**", "/member/delete/**", "/board/**").hasAnyRole("MEMBER", "SELLER", "ADMIN")
        // 권한 이외의 페이지는 모두 허용
//                .anyRequest().permitAll();

        // 로그인 설정
        http.formLogin()
                .loginPage("/member/login") // 로그인 페이지로 이동
                .usernameParameter("memberEmail")
                .passwordParameter("memberPassword")
                .loginProcessingUrl("/member/login") // form에서 post방식으로 설정하는 URL
                .defaultSuccessUrl("/")
                .failureUrl("/member/login")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/member/login")
                .userInfoEndpoint()
                .userService(myAuth2UserService());

        // 로그아웃 설정
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> myAuth2UserService() {
        return new MyOAuth2UserService();
    }

}
