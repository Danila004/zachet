package com.vsu.zachet.service;

import com.vsu.zachet.repository.UserRepository;
import com.vsu.zachet.request.AddUserRequest;
import com.vsu.zachet.request.EditUserRequest;
import com.vsu.zachet.response.UserAuthResponse;
import com.vsu.zachet.response.UserResponse;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse setUser(Long id, EditUserRequest request) {
        return userRepository.editUser(id, request);
    }

    public UserResponse deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }

    public List<UserResponse> getUsers(int page) {
        return userRepository.getUsers(page);
    }

    @Transactional
    public UserResponse addUser(AddUserRequest request) {
        userRepository.lockOnUsername(request.username());
        Optional<UserAuthResponse> opUser = userRepository.findAuthUser(request.username());
        if(opUser.isPresent())
            throw new ValidationException("Такой пользователь уже есть");
        return userRepository.addUser(request);
    }
}
