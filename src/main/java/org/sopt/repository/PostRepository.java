package org.sopt.repository;

import java.util.List;

import org.sopt.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByTitleContentContaining(String keyword);
}
