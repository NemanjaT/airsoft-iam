package com.ntozic.airsoft.iam.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!isHeaderValid(authHeader)) {
            logger.debug("Authorization header is invalid");
            filterChain.doFilter(request, response);
            return;
        }

        var token = jwtTokenUtil.getTokenFromHeader(authHeader);
        if (!jwtTokenUtil.isValid(token)) {
            logger.debug("JWT token is invalid");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var authentication = new UsernamePasswordAuthenticationToken(
                    jwtTokenUtil.getReference(token),
                    null,
                    List.of(() -> jwtTokenUtil.getAuthority(token))
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exception) {
            logger.debug("Could not resolve user and set it on the Spring Security Context", exception);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isHeaderValid(final String authHeader) {
        return authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ");
    }
}
