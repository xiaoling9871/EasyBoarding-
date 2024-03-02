package com.example.authserver.controller;

import com.example.authserver.domain.request.RegisterUserDto;
import com.example.authserver.domain.response.ResponseDto;
import com.example.authserver.entity.User;
import com.example.authserver.exception.IdNotFoundException;
import com.example.authserver.repository.UserRepository;
import com.example.authserver.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public ResponseDto loginUser(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        String token = authService.loginUser(username, password);
        return ResponseDto.builder()
                .message("Login Success!")
                .data(token)
                .build();
    }

    @PostMapping("/register")
    public ResponseDto registerUser(@RequestBody @Validated RegisterUserDto registerUserDto) {
        User newUser = authService.registerUser(registerUserDto);
        return ResponseDto.builder()
                .message("Register success!")
                .data(newUser)
                .build();
    }

    @GetMapping("/check/mail")
    public Boolean checkIfMailExist(@RequestParam String email) {
        return authService.checkIfEmailExist(email);
    }

//    @GetMapping("/check/house")
//    public Boolean checkIfUserAlreadyHasHouse(@RequestParam Integer userId) {
//        return authService.findUserById(userId).getHouseId() != null;
//    }

    @GetMapping("/check/house")
    public Boolean checkIfUserAlreadyHasHouse(@RequestParam String emailAddress) {
        Optional<User> possibleUser = userRepository.findByEmail(emailAddress);
        possibleUser.orElseThrow(IdNotFoundException::new);
        return possibleUser.get().getHouseId() != null;
    }
    @PatchMapping("/housing")
    public ResponseDto assignHouseIdToUser(@RequestParam(name = "email") String email,
                                           @RequestParam(name = "houseId") Integer houseId) {
        User user = authService.assignHouseIdToUser(email, houseId);
        return ResponseDto.builder()
                .message("Update Success!")
                .data(user)
                .build();
    }

    @GetMapping("/housing/{userId}")
    public Integer getHouseIdByUserId(@PathVariable Integer userId) {
        Optional<User> possibleUser = userRepository.findById(userId);
        possibleUser.orElseThrow(IdNotFoundException::new);
        User user = possibleUser.get();
        return user.getHouseId();
    }
}
