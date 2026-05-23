package com.vsu.zachet.response;

public record CommentResponse(
        Long id,
        Long postId,
        Long userId,
        String content
) {
}
