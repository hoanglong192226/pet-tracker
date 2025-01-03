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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
  public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
    http.cors(c -> c.configurationSource(corsConfigurationSource))
        .csrf().disable()
        .httpBasic().disable()
        .authorizeHttpRequests()
        .antMatchers("/api/v1/admin/**").hasRole(UserRole.ADMIN.getValue())
        .antMatchers("/api/v1/pets/**").hasAnyRole(UserRole.ADMIN.getValue(), UserRole.MEMBER.getValue())
        .antMatchers("/api/v1/owners/**").hasAnyRole(UserRole.ADMIN.getValue(), UserRole.MEMBER.getValue())
        .antMatchers("/api/v1/auth/login", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
        .anyRequest().authenticated();
    return http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedOrigin("http://ui:3000");

    configuration.addAllowedHeader("*");

    configuration.setAllowCredentials(true);

    configuration.addAllowedMethod("GET");
    configuration.addAllowedMethod("POST");
    configuration.addAllowedMethod("PUT");
    configuration.addAllowedMethod("DELETE");

    // Apply configurations to specific paths
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
