package org.example.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain adminSecurityChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
                .securityMatcher("/user/**")
                .logout(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain userSecurityChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/user/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().hasAnyRole("ADMIN", "USER"))
                .logout(Customizer.withDefaults())
                .securityMatcher("/user/**").build();
    }
    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/")
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain registrationSecurityChain (HttpSecurity http) throws Exception {
        return http.securityMatcher("/registration").build();
    }
}
