package org.sopt.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByTitle_ContentContaining(String keyword);

	boolean existsByTitle_Content(String title);

	Optional<Post> findTopByOrderByCreatedAtDesc();
}
