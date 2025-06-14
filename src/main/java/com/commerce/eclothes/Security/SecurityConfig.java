package com.commerce.eclothes.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.commerce.eclothes.Services.CustomOAuth2UserService;
import com.commerce.eclothes.Services.CustomUserDetailsService;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private CustomOAuth2UserService customOAuth2UserService;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/register", "/login", "/img/**", "/css/**",
                                                                "/js/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login") // Ajout important
                                                .usernameParameter("email") // Correspond au champ du formulaire
                                                .passwordParameter("password") // Correspond au champ du formulaire
                                                .defaultSuccessUrl("/dashboard", true)
                                                .successHandler(authenticationSuccessHandler())
                                                .permitAll())
                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/login")
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userService(customOAuth2UserService))
                                                .successHandler(oAuth2AuthenticationSuccessHandler())
                                                .defaultSuccessUrl("/dashboard", true))
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/logout-success")
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .deleteCookies("JSESSIONID"));

                return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
                return (request, response, authentication) -> {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        HttpSession session = request.getSession();
                        session.setAttribute("user", userDetails);
                        response.sendRedirect("/dashboard");
                };
        }

        @Bean
        public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
                return (request, response, authentication) -> {
                        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                        HttpSession session = request.getSession();
                        session.setAttribute("user", oauthUser);
                        response.sendRedirect("/dashboard");
                };
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder())
                                .and()
                                .build();
        }
}