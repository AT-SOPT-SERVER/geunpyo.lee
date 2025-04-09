package org.example.domain;

import java.time.LocalDateTime;
import org.example.util.StringUtils;

public class Post {
    private final int id;
    private String title;
    private final LocalDateTime createdAt;

    public Post(int id, String title) {
        validateTitle(title);
        this.id = id;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public Post(int id, String title, LocalDateTime createdAt) {
        validateTitle(title);
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updatePost(String title) {
        validateTitle(title);
        this.title = title;
    }

    private void validateTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        }

        if (StringUtils.getLength(title) > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘길 수 없습니다. ");
        }
    }
}
