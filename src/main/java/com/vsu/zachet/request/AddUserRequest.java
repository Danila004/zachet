package com.vsu.zachet.request;

import org.springframework.stereotype.Component;

public record AddUserRequest (
    String username,
    String email,
    String password_hash
) {}
