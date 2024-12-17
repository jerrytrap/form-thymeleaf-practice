package com.example.tem;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Post {
    private static long lastId = 0;

    @Builder.Default
    private long id = ++lastId;
    private String title;
    private String content;
}
