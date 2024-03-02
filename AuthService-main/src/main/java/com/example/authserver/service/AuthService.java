package com.example.authserver.service;

import com.example.authserver.domain.request.RegisterUserDto;
import com.example.authserver.entity.Role;
import com.example.authserver.exception.EmailNotFoundException;
import com.example.authserver.exception.EmailTakenException;
import com.example.authserver.exception.IdNotFoundException;
import com.example.authserver.exception.UsernameTakenException;
import com.example.authserver.repository.RoleRepository;
import com.example.authserver.entity.User;
import com.example.authserver.repository.UserRepository;
import com.example.authserver.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public Boolean checkIfEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String loginUser(String username, String password) {
        Optional<User> possibleUser =   userRepository.findByUsername(username);
        if (!possibleUser.isPresent()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtProvider.createToken((UserDetails) authentication.getPrincipal(), possibleUser.get().getId());
    }

    public User findUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(IdNotFoundException::new);
        return user.get();
    }

    public User registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new EmailTakenException();
        }

        if (userRepository.findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new UsernameTakenException();
        }

        Role emp = roleRepository.findByRoleName("EMP").get();
        User user = User.builder()
                .username(registerUserDto.getUsername())
                .email(registerUserDto.getEmail())
                .password(registerUserDto.getPassword())
                .roles(Collections.singletonList(emp))
                .activeFlag(true)
                .build();
        return userRepository.save(user);
    }

    public User assignHouseIdToUser(String email, Integer houseId) {
        Optional<User> possibleUser = userRepository.findByEmail(email);
        possibleUser.orElseThrow(EmailNotFoundException::new);
        User user = possibleUser.get();
        user.setHouseId(houseId);
        return userRepository.save(user);
    }
}
