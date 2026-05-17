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
    public ResponseEntity<String> setUser(@PathVariable Long id, @RequestBody EditUserRequest request) {
        userService.setUser(id, request);
        return ResponseEntity.ok("Информация обновлена");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Пользователь удален");
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
