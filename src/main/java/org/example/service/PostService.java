package org.example.service;

import java.util.List;
import org.example.domain.Post;
import org.example.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private int postId = 1;
    private final String EXCEPTION_FORMAT = "서비스 처리중 예외 발생: %s";

    public void createPost(String title) {
        try {
            Post post = new Post(postId++, title);
            postRepository.save(post);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, e.getMessage()));
        }
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
        try {
            isValidId(postId);
            Post post = postRepository.findPostById(postId);
            post.updatePost(title);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, e.getMessage()));
        }
    }

    public void isValidId(int id) {
        if (!postRepository.isPresent(id)) {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        }
    }

}
