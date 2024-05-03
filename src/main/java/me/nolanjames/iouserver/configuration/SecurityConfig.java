package me.nolanjames.iouserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configure -> configure.configurationSource(null))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/user/delete/**").hasAuthority("DELETE:USER")
                                .requestMatchers(HttpMethod.DELETE, "/customer/delete/**").hasAuthority("DELETE:CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE, "/customer/delete/**").hasAuthority("DELETE:CUSTOMER")
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exception ->
                        exception
                                .accessDeniedHandler(null)
                                .authenticationEntryPoint(null))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {

    }
}
