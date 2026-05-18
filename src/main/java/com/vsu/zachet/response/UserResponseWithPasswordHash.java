package com.vsu.zachet.response;

public record UserResponseWithPasswordHash(
        Long id,
        String username,
        String email,
        String password_hash
) { }
