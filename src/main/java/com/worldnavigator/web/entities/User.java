package com.worldnavigator.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public User(
            String name,
            String username,
            String password
    ) {
        this(name, username, password, true, Collections.emptyList());
    }

    public User(String name,
                String username,
                String password,
                boolean enabled,
                List<Authority> authorities
    ) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

