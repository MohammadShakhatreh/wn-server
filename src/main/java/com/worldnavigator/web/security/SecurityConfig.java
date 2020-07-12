package com.worldnavigator.web.security;

import com.worldnavigator.web.jwt.JwtConfig;
import com.worldnavigator.web.jwt.JwtConfigurer;
import com.worldnavigator.web.jwt.JwtFilter;
import com.worldnavigator.web.services.AccountService;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import javax.crypto.SecretKey;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final AccountService accountService;

    @Autowired
    public SecurityConfig(JwtConfig jwtConfig, AccountService accountService) {
        this.jwtConfig = jwtConfig;
        this.accountService = accountService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .apply(new JwtConfigurer(jwtConfig, secretKey(), userDetailsService()));

        // Authentication endpoints security
        http
            .csrf().disable()
            .authorizeRequests()
            .mvcMatchers("/auth/login")
                .permitAll();

        // Accounts endpoints security
        http
            .authorizeRequests()
                .mvcMatchers(DELETE, "/accounts/@{username}")
                    .access("#username == principal?.username or hasRole('ROLE_ADMIN')")
                .mvcMatchers("/accounts/**")
                    .permitAll();

        // Testing authentication
        http
            .authorizeRequests()
                .mvcMatchers("/test")
                    .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return accountService;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));
    }
}
