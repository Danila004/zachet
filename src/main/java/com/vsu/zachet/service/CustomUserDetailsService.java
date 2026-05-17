package com.vsu.zachet.service;

import com.vsu.zachet.repository.UserRepository;
import com.vsu.zachet.response.UserAuthResponse;
import com.vsu.zachet.response.UserResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserAuthResponse> opUser = userRepository.findAuthUser(username);
        if (opUser.isEmpty())
            throw new AuthenticationException("Нет такого пользователя или неверный пароль") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        return User.builder()
                .username(opUser.get().username())
                .password(opUser.get().password_hash())
                .roles(new String[0])
                .build();
    }
}
