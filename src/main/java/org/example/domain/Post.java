package org.example.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private final int id;
    private Title title;
    private final LocalDateTime createdAt;

    public Post(int id, String title) {
        this.id = id;
        this.title = new Title(title);
        this.createdAt = LocalDateTime.now();
    }

    public Post(int id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = new Title(title);
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updatePost(String title) {
        this.title = new Title(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id &&
                Objects.equals(title, post.title) &&
                Objects.equals(createdAt, post.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, createdAt);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
