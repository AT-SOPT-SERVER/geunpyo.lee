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

    //TODO: 검증 추가
    public void updatePost(int postId, String title) {
        Post post = getPostById(postId);
        post.updatePost(title);
    }
}
