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

    public boolean updatePost(int postId, String title) {
        boolean isValid = isValidPost(postId);
        if (isValid) {
            Post post = postRepository.findPostById(postId);
            post.updatePost(title);
        }
        return false;
    }

    public boolean isValidPost(int id) {
        if (!postRepository.isPresent(id)) {
            System.out.println("게시물이 존재하지 않습니다.");
            return false;
        }
        return true;
    }

}
