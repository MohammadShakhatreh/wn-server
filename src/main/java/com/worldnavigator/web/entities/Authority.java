package com.worldnavigator.web.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorities")
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
public class Authority implements GrantedAuthority {

    @Id
    private String role;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    @Override
    public String getAuthority() {
        return role;
    }
}
