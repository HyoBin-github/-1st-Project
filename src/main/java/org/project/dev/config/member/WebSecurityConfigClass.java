package org.project.dev.config.member;

import org.project.dev.config.semiMember.SemiUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigClass{

    @Configuration
    @Order(1)
    public static class UserConfig{

        @Bean
        public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests()
                    .antMatchers("/", "/member/join", "/member/login").permitAll()
                    .antMatchers("/member/logout", "/member/detail", "/member/update/**", "/member/delete/**", "/board/**", "/cart/**").authenticated()
                    .antMatchers("/product/manage").hasAnyRole("SELLER", "ADMIN")

                    .and()
                    .formLogin()
                    .loginPage("/member/login")
                    .usernameParameter("memberEmail")
                    .passwordParameter("memberPassword")
                    .loginProcessingUrl("/member/login/post")
                    .failureUrl("/login")
                    .defaultSuccessUrl("/")

                    .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(myAuth2UserService());

            http.logout()
                    .logoutUrl("/member/logout")
                    .logoutSuccessUrl("/")

//                    .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/403")

                    .and()
                    .csrf().disable()
                    .authenticationProvider(userAuthenticationProvider());
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

        @Bean
        public UserDetailsServiceImpl userDetailsService(){
            return new UserDetailsServiceImpl();
        }

        @Bean
        public DaoAuthenticationProvider userAuthenticationProvider(){
            DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService());
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }
    }

    @Configuration
    @Order(2)
    public static class SemiUserConfig {

        @Bean
        public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {
            http.antMatcher("/semiMember/**")
                    .authorizeHttpRequests()
                    .antMatchers("/", "/login").permitAll()
//            http.authorizeHttpRequests()
//                    .antMatchers("/", "/semiMember/join", "/semiMember/login").permitAll()
//                    .antMatchers("/member/logout", "/board/**").authenticated()

                    .and()
                    .formLogin()
                    .loginPage("/semiMember/login")
                    .usernameParameter("semiMemberEmail")
                    .passwordParameter("semiMemberPhone")
                    .loginProcessingUrl("/semiMember/login/post")
                    .failureUrl("/login")
                    .defaultSuccessUrl("/")

                    .and()
                    .logout()
                    .logoutUrl("/semiMember/logout")
                    .logoutSuccessUrl("/")

//                    .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/403")

                    .and()
                    .csrf().disable()
                    .authenticationProvider(semiUserAuthenticationProvider());
            return http.build();
        }

        @Bean
        public SemiUserDetailsServiceImpl semiUserDetailsService() {
            return new SemiUserDetailsServiceImpl();
        }

        @Bean
        public PasswordEncoder semiPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider semiUserAuthenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(semiUserDetailsService());
            provider.setPasswordEncoder(semiPasswordEncoder());
            return provider;
        }
    }
}