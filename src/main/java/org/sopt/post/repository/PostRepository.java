package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
	boolean existsByTitle_Content(String title);

	List<Post> findAllOrderByOrderByCreatedAtDesc();
}
