package com.springboot.springboot_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v1/index2").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.permitAll();
                    form.successHandler(successHandler()); // url a donde se irá luego de iniciar sesión
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    session.invalidSessionUrl("/login"); // Redirige a /login si la sesión es inválida
                    session.maximumSessions(1); // Permite solo una sesión por usuario
                    session.sessionFixation().migrateSession(); // Protege contra ataques de fijación de sesión
                })
                .build();
    }

    public AuthenticationSuccessHandler successHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/v1/session");
        };
    }

}
