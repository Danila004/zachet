package com.vsu.zachet.controller;

import com.vsu.zachet.request.AddUserRequest;
import com.vsu.zachet.request.EditUserRequest;
import com.vsu.zachet.response.UserResponse;
import com.vsu.zachet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping(path = "/{id}")
    public UserResponse setUser(@PathVariable Long id, @RequestBody EditUserRequest request) {
        return userService.setUser(id, request);
    }

    @DeleteMapping(path = "/{id}")
    public UserResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping
    public List<UserResponse> getUser(@RequestParam int page) {
        return userService.getUsers(page);
    }

    @PostMapping
    public UserResponse addUser(@RequestBody AddUserRequest request) {
        return userService.addUser(request);
    }
}
