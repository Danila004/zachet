package com.vsu.zachet.request;

public record AddCommentRequest(
        Long postId,
        Long userId,
        String content
) { }
