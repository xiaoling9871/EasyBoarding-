package com.example.authserver.service;

import com.example.authserver.entity.User;
import com.example.authserver.repository.UserRepository;
import com.example.authserver.security.AuthUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> possibleUser = userRepository.findByUsername(username);
        if (!possibleUser.isPresent()) {
            throw new UsernameNotFoundException("username not found!");
        }
        User user = possibleUser.get();
        return AuthUserDetails.builder()
                .username(user.getUsername())
                .authorities(getAuthoritiesFromUser(user))
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(user.getActiveFlag())
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
