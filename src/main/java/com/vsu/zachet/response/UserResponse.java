package com.vsu.zachet.response;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
