package org.sopt.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.domain.Post;
import org.sopt.domain.constant.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByTagAndTitle_ContentContainingOrTagAndUser_NameContaining(Tag tag, String titleKeyword, Tag tag2,
		String nameKeyword);

	List<Post> findByTitle_ContentContainingOrUser_NameContaining(String titleKeyword, String nameKeyword);

	List<Post> findAllByTag(Tag tag);

	boolean existsByTitle_Content(String title);

	Optional<Post> findTopByOrderByCreatedAtDesc();
}
