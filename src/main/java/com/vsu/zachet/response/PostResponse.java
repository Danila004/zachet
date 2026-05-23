package com.vsu.zachet.response;

public record PostResponse(
        Long id,
        Long userId,
        String title,
        String content
) {
}
