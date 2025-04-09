package org.example.controller;

import java.util.List;
import org.example.domain.Post;
import org.example.repository.PostRepositoryFactory;
import org.example.service.PostService;

public class PostController {
    private final PostService postService = new PostService(PostRepositoryFactory.createMarkdownRepository("posts.md"));

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

    public void updatePost(int id, String title) {
        postService.updatePost(id, title);
    }
}
