package com.vsu.zachet.request;

import org.springframework.stereotype.Component;

public record AddPostRequest (
        Long userId,
        String title,
        String content
) {}
