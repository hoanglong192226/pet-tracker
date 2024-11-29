package com.pet.management.tracker.filter;

import com.pet.management.tracker.util.CookiesUtil;
import com.pet.management.tracker.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = null;
    for (Cookie cookie : request.getCookies()) {
      if (CookiesUtil.TOKEN_COOKIE.equals(cookie.getName())) {
        token = cookie.getValue();
      }
    }

    if (token != null) {
      if (jwtUtil.isTokenExpired(token)) {
        SecurityContextHolder.clearContext();

        Cookie expiredCookie = new Cookie(CookiesUtil.TOKEN_COOKIE, null);
        expiredCookie.setHttpOnly(true);
        expiredCookie.setSecure(true);
        expiredCookie.setPath("/");
        expiredCookie.setMaxAge(0);
        response.addCookie(expiredCookie);

        filterChain.doFilter(request, response);
        return;
      }

      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        String username = jwtUtil.extractClaims(token).getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (username.equals(userDetails.getUsername())) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
              null,
              userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }

    filterChain.doFilter(request, response);
  }
}
