package com.vsu.zachet.response;

public record UserAuthResponse (
        String username,
        String password_hash
) {}
