package com.vsu.zachet.request;

import org.springframework.stereotype.Component;

public record AddPostRequest (
        Long user_id,
        String title,
        String content
) {}
