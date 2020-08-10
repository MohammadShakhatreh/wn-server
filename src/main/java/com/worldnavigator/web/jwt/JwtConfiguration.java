package com.worldnavigator.web.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfiguration {

    private final String prefix;

    private final String secret;

    private final int expireInDays;

    public JwtConfiguration(
            @DefaultValue("Bearer") String prefix,
            String secret,
            @DefaultValue("14") int expireInDays
    ) {
        this.prefix = prefix;
        this.secret = secret;
        this.expireInDays = expireInDays;
    }
}
