package com.vsu.zachet.controller;

import com.vsu.zachet.AbstractIntegrationTest;
import com.vsu.zachet.repository.UserRepository;
import com.vsu.zachet.request.AddUserRequest;
import com.vsu.zachet.request.EditUserRequest;
import com.vsu.zachet.response.UserResponse;
import com.vsu.zachet.response.UserResponseWithPasswordHash;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends AbstractIntegrationTest {
    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void cleanUsers() {
        jdbcTemplate.update("""
            DELETE FROM users
        """);
    }

    @Test
    public void GivenFiveUsersInDBWhenGetUsersThenGetThem() {
        for(int i = 0; i < 5; i++) {
            userRepository.addUser(new AddUserRequest("Bob" + i, "bob" + i + "@mail.ru", "12345"));
        }

        List<UserResponse> users = userController.getUser(0);

        assertEquals(5, users.toArray().length);
    }

    @Test
    public void GivenNewUserWhenAddHimInDBThenSuccessfulAdding() {
        AddUserRequest newUser = new AddUserRequest("Bob", "bob@mail.ru", "12345");

        userController.addUser(newUser);

        Optional<UserResponseWithPasswordHash> newUserInDB = userRepository.findUser(newUser.username());
        assertTrue(newUserInDB.isPresent());
    }

    @Test
    public void GivenUserInDbWhenDeleteHimThenSuccessfulDeleting() {
        AddUserRequest newUser = new AddUserRequest("Bob", "bob@mail.ru", "12345");
        UserResponse userInDb = userRepository.addUser(newUser);

        userController.deleteUser(userInDb.id());

        Optional<UserResponseWithPasswordHash> newUserInDB = userRepository.findUser(newUser.username());
        assertTrue(newUserInDB.isEmpty());
    }

    @Test
    public void GivenUserInDbWhenEditHimThenSuccessfulChanging() {
        AddUserRequest newUser = new AddUserRequest("Bob", "bob@mail.ru", "12345");
        UserResponse userInDb = userRepository.addUser(newUser);

        EditUserRequest editUser = new EditUserRequest("newbob@mail.ru", "54321");
        userController.setUser(userInDb.id(), editUser);

        Optional<UserResponseWithPasswordHash> newUserInDB = userRepository.findUser(newUser.username());
        assertEquals(editUser.email(), newUserInDB.get().email());
        assertTrue(passwordEncoder.matches(editUser.password_hash(), newUserInDB.get().password_hash()));
    }

    @Test
    public void GivenExistedUserWhenAddHimInDBThenValidationException() {
        AddUserRequest newUser = new AddUserRequest("Bob", "bob@mail.ru", "12345");
        userRepository.addUser(newUser);

        assertThrowsExactly(ValidationException.class, () -> userController.addUser(newUser));
    }
}
