package com.worldnavigator.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private final UserDetailsService userDetailsService;

    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    public JwtFilter(JwtConfiguration jwtConfiguration,
                     SecretKey secretKey,
                     UserDetailsService userDetailsService) {
        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        try {

            HttpServletRequest request = (HttpServletRequest) servletRequest;

            String token = extractToken(request);

            if(StringUtils.hasText(token)) {

                Jws<Claims> claimsJws = Jwts
                        .parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();
                String username = body.getSubject();

                UserDetails user = userDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtException e) {
            throw new IllegalStateException("The token is not valid!");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authorizationHeader)
                && authorizationHeader.startsWith(jwtConfiguration.getPrefix() + " "))
            return authorizationHeader.split(" ")[1];

        return null;
    }


//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response) {
//
//        try {
//            UsernamePasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
//                    .readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getUsername(),
//                    authenticationRequest.getPassword()
//            );
//
//
//            return authenticationManager.authenticate(authentication);
//
//        } catch (IOException e) {
//            throw new RuntimeException("Request format is not valid!");
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//
//        String token = Jwts.builder()
//                .setSubject(authResult.getName())
//                .setIssuedAt(Date.from(Instant.now()))
//                .setExpiration(Date.from(Instant.now().plus(jwtConfig.getExpireInDays(), ChronoUnit.DAYS)))
//                .signWith(secretKey)
//                .compact();
//
//        String authorizationHeaderValue = String.format("%s %s", jwtConfig.getPrefix(), token);
//
//        response.addHeader(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);
//        response.setStatus(HttpStatus.CREATED.value());
//    }
}
