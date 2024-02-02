package ru.pariy.tmsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pariy.tmsystem.dto.LoginUserDto;
import ru.pariy.tmsystem.model.User;
import ru.pariy.tmsystem.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

//    private final MyUserDetailsService myUserDetailsService;
    @Autowired
    public AuthenticationService(
            UserRepository userRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

    }

//    public User signup(RegisterUserDto input) {
//        User user = new User()
//                .setFullName(input.getFullName())
//                .setEmail(input.getEmail())
//                .setPassword(passwordEncoder.encode(input.getPassword()));
//
//        return userRepository.save(user);
//    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword())
        );

        return userRepository.getByEmail(input.getEmail());
//        return userRepository.getByEmail(input.getEmail());
//                .orElseThrow();
    }
}
