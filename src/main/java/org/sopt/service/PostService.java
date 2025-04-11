package org.sopt.service;

import static org.sopt.util.IdUtils.generateNextId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository;
    private static final Duration POST_CREATION_COOLDOWN = Duration.ofMinutes(3);
    private final String EXCEPTION_FORMAT = "서비스 처리중 예외 발생: %s";

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(String title) {
        List<Post> allPosts = postRepository.findAll();
        checkDuplicate(allPosts, title);
        checkLastPostTime(allPosts);
        Post post = new Post(generateNextId(allPosts), title);

        postRepository.save(post);
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
            List<Post> allPosts = postRepository.findAll();
            checkDuplicate(allPosts, title);
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
            post.updatePost(title);
            postRepository.update(post);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(EXCEPTION_FORMAT, e.getMessage()));
        }
    }

    public List<Post> searchPost(String keyword) {
        if (keyword.length() < 2) {
            throw new IllegalArgumentException("검색은 두 글자 이상부터 가능합니다.");
        }
        return postRepository.findByTitleContaining(keyword);
    }

    private void checkLastPostTime(List<Post> posts) {

        if (posts.isEmpty()) {
            return;
        }

        Post latestPost = posts.stream()
                .max(Comparator.comparing(Post::getCreatedAt))
                .orElse(null);

        LocalDateTime now = LocalDateTime.now();
        Duration sinceLastPost = Duration.between(latestPost.getCreatedAt(), now);

        if (sinceLastPost.compareTo(POST_CREATION_COOLDOWN) < 0) {
            long remainingSeconds = POST_CREATION_COOLDOWN.minus(sinceLastPost).getSeconds();
            throw new RuntimeException("도배 방지를 위해 " + remainingSeconds + "초 후에 다시 시도해주세요.");
        }
    }

    private void checkDuplicate(List<Post> posts, String title) {
        String normalizedTitle = normalizeTitle(title);

        List<Post> duplicates = posts.stream()
                .filter(post -> normalizeTitle(post.getTitle()).equals(normalizedTitle))
                .toList();

        if (!duplicates.isEmpty()) {
            throw new RuntimeException("이미 동일한 내용의 게시물이 있습니다.");
        }
    }

    private String normalizeTitle(String title) {
        if (title == null) {
            return "";
        }
        return title.trim()
                .toLowerCase()
                .replaceAll("\\s+", " "); // 여러 공백을 하나로 통일
    }
}
