package com.anurag.cs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.anurag.cs.filter.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
      private final JwtAuthenticationFilter jwtFilter;
      public SecurityConfig(JwtAuthenticationFilter jwtFilter) { this.jwtFilter = jwtFilter; }

      @Bean
      public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(c -> c.disable()).cors(Customizer.withDefaults())
                        .authorizeHttpRequests(a -> a
                                    .requestMatchers("/", "/health", "/students/login", "/students/register", "/error", "/uploads/**").permitAll()
                                    .requestMatchers("/students/**").hasRole("ADMIN") // user management: admin only
                                    .anyRequest().authenticated()) // fine-grained rules via @PreAuthorize
                        .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
      }
}
