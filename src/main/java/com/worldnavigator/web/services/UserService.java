package com.worldnavigator.web.services;

import com.worldnavigator.web.dto.UserDto;
import com.worldnavigator.web.entities.User;
import com.worldnavigator.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public User create(UserDto data) {

        Optional<User> optional = userRepository.findById(data.getUsername());

        if(optional.isPresent())
            throw new IllegalArgumentException("There is a user with this username.");

        User account = new User(
                data.getName(),
                data.getUsername().trim().toLowerCase(),
                passwordEncoder.encode(data.getPassword())
        );

        return userRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("There is no account with %s as username!", username)));
    }
}
