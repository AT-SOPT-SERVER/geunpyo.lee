package org.example.service;

import java.util.List;
import org.example.domain.Post;
import org.example.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private int postId = 1;

    public void createPost(String title) {
        Post post = new Post(postId++, title);
        postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findPostById(id);
    }

    public boolean deletePostById(int id) {
        return postRepository.delete(id);
    }

    public void updatePost(int postId, String title) {
        isValidPost(postId);
        Post post = postRepository.findPostById(postId);
        post.updatePost(title);
    }

    public void isValidPost(int id) {
        if (!postRepository.isPresent(id)) {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        }
    }

}
