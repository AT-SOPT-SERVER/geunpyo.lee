package org.sopt.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(int id);

    List<Post> findAll();

    void update(Post post);

    boolean delete(int id);

    boolean isPresent(int id);

    List<Post> findByTitleContaining(String titleKeyword);
}
