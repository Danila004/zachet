package com.vsu.zachet.request;

public record EditUserRequest(
        String email,
        String passwordHash
) { }
