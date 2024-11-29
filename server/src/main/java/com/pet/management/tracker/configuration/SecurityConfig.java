package com.pet.management.tracker.configuration;

import com.pet.management.tracker.filter.JwtFilter;
import com.pet.management.tracker.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .httpBasic().disable()
        .authorizeRequests()
        .antMatchers("/api/v1/admin/**").hasRole(UserRole.ADMIN.getValue())
        .antMatchers("/api/v1/pets/**").hasAnyRole(UserRole.ADMIN.getValue(), UserRole.MEMBER.getValue())
        .antMatchers("/api/v1/owners/**").hasAnyRole(UserRole.ADMIN.getValue(), UserRole.MEMBER.getValue())
        .antMatchers("/api/v1/auth/login", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
        .anyRequest().authenticated();
    return http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
  }
}
