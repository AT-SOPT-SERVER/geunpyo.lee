package org.example.service;

import static org.example.util.IdUtils.generateNextId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.example.domain.Post;
import org.example.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository;
    private static final Duration POST_CREATION_COOLDOWN = Duration.ofMinutes(3);

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private final String EXCEPTION_FORMAT = "서비스 처리중 예외 발생: %s";

    public Post createPost(String title) {
        List<Post> allPosts = postRepository.findAll();
        checkLastPostTime(allPosts);
        Post post = new Post(generateNextId(allPosts), title);

        return postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findById(id).orElseGet(null);
    }

    public boolean deletePostById(int id) {
        return postRepository.delete(id);
    }

    public void updatePost(int postId, String title) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
            post.updatePost(title);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, e.getMessage()));
        }
    }

    private void checkLastPostTime(List<Post> posts) {

        if (posts.isEmpty()) {
            return;
        }

        Post latestPost = posts.stream()
                .max(Comparator.comparing(Post::getCreatedAt))
                .orElse(null);

        if (latestPost == null) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        Duration sinceLastPost = Duration.between(latestPost.getCreatedAt(), now);

        if (sinceLastPost.compareTo(POST_CREATION_COOLDOWN) < 0) {

            long remainingSeconds = POST_CREATION_COOLDOWN.minus(sinceLastPost).getSeconds();
            throw new RuntimeException("도배 방지를 위해 " + remainingSeconds + "초 후에 다시 시도해주세요.");
        }
    }

}
