package org.sopt.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.sopt.domain.constant.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {
	// PostRepository 인터페이스에 추가
	@Query("SELECT p FROM Post p WHERE " +
		"(:tag IS NULL OR p.tag = :tag) AND " +
		"(:keyword IS NULL OR LOWER(p.title.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		"LOWER(p.user.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Post> findByKeywordAndTagDynamically(
		@Param("keyword") String keyword,
		@Param("tag") Tag tag);

	boolean existsByTitle_Content(String title);

	Optional<Post> findTopByOrderByCreatedAtDesc();
}
