package org.sopt.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.sopt.domain.constant.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {
	@Query("SELECT p FROM Post p WHERE " +
		"(:tag IS NULL OR p.tag = :tag) AND " +
		"(:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		"LOWER(p.user.name) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
		"ORDER BY p.createdAt DESC ")
	List<Post> findByKeywordAndTagDynamically(
		@Param("keyword") String keyword,
		@Param("tag") Tag tag);

	boolean existsByTitle_Content(String title);

	Optional<Post> findTopByOrderByCreatedAtDesc();

	List<Post> findAllOrderByOrderByCreatedAtDesc();
}
