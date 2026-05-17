package com.vsu.zachet.request;

public record AddCommentRequest(
        Long post_id,
        Long user_id,
        String content
) { }
