package com.example.UberProject_AuthService.filters;

import com.example.UberProject_AuthService.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String token  = null;
        if(request.getCookies() != null)
        {
            for(Cookie cookie : request.getCookies())
            {
                if(cookie.getName().equals("JwtToken"))
                {
                    token = cookie.getValue();
                }

            }
        }

        if(token == null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
            // user has not provided any token so request should not go forward
        }

        String email = jwtService.extractEmail(token);

        if(email != null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token, userDetails.getUsername()))
            {
                // we did not need to chaek manually whether user is authenticated or not below class handles it carefully
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }

        }

        filterChain.doFilter(request,response);
    }
}
