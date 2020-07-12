package com.worldnavigator.web.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "accounts")
@EqualsAndHashCode(exclude = "accounts")
public class Authority implements GrantedAuthority {

    @Id
    private String role;

    @ManyToMany(mappedBy = "authorities")
    private List<Account> accounts;

    @Override
    public String getAuthority() {
        return role;
    }
}
