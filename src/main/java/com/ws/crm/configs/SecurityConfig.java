package com.ws.crm.configs;

import com.ws.crm.services.implementations.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/images/**", "/js/**", "/styles/**").permitAll()
                                .requestMatchers("/login", "/registration", "/error", "/error/400", "/error/403",
                                        "/error/404", "/error/409", "/users/profile").permitAll()
                                .requestMatchers("/users/**","/user-project/**").hasRole("ADMIN")
                                .requestMatchers("/items/*/edit", "/orders/*/edit", "/projects/*/edit",
                                        "/projects/new").hasAnyRole("ADMIN", "SUPPLIER")
                                .requestMatchers("/items/new", "/orders/new", "/items/newByOrder/*")
                                        .hasAnyRole("ADMIN", "MANAGER")
                                .anyRequest().hasAnyRole("MANAGER", "SUPPLIER", "ADMIN")
                )
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/", true)
                .failureForwardUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
