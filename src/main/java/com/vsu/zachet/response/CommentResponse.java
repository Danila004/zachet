package com.vsu.zachet.response;

public record CommentResponse(
        Long id,
        Long post_id,
        Long user_id,
        String content
) {
}
