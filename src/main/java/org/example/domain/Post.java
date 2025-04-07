package org.example.domain;

public class Post {
    private final int id;
    private String title;

    public Post(int id, String title) {
        validateNull(title);
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void updatePost(String title) {
        validateNull(title);
        this.title = title;
    }

    private void validateNull(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        }
    }
}
