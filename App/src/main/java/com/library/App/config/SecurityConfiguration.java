package com.library.App.config;

import com.library.App.service.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private  AuthManager authManager;
    @Autowired
    private  JwtFilter jwtFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security)throws Exception{
        return security
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/login","/api/register").permitAll()
                        .requestMatchers(GET, "/api/user/me").authenticated()
                        .requestMatchers(GET, "/api/user/{username}").access(authManager)

                        .requestMatchers(GET,"/api/user/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_LIBRARIAN")

                        .requestMatchers(POST, "/api/book").hasAnyAuthority( "ROLE_ADMIN", "ROLE_LIBRARIAN")
                        .requestMatchers(POST, "/api/book/{bookId}").hasAnyAuthority( "ROLE_ADMIN", "ROLE_LIBRARIAN")

                        .requestMatchers(PUT, "/api//book/{bookId}").hasAnyAuthority( "ROLE_ADMIN", "ROLE_LIBRARIAN")

                        .requestMatchers(DELETE, "/api//book/{bookId}").hasAnyAuthority( "ROLE_ADMIN", "ROLE_LIBRARIAN")


                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(token -> token.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
}

