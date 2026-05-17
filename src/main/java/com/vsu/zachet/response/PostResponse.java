package com.vsu.zachet.response;

public record PostResponse(
        Long id,
        Long user_id,
        String title,
        String content
) {
}
