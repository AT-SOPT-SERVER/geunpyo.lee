package org.sopt.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.sopt.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByTitleContentContaining(String keyword);

	boolean existsByTitle(Title title);

	Optional<Post> findTopByOrderByCreatedAtDesc();
}
