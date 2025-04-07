package org.example.controller;

import java.util.List;
import org.example.domain.Post;
import org.example.service.PostService;

public class PostController {
    private final PostService postService = new PostService();

    public void createPost(final String title) {
        postService.createPost(title);
    }

    public List<Post> getAllPosts() {
        return postService.getAllPost();
    }

    public Post getPostById(int id) {
        return postService.getPostById(id);
    }

    public boolean deletePostById(int id) {
        return postService.deletePostById(id);
    }

    public boolean updatePost(int id, String title) {
        return postService.updatePost(id, title);
    }
}
