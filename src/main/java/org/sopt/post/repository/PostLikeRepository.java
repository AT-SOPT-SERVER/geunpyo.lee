package org.sopt.post.repository;

import java.util.Optional;

import org.sopt.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	Optional<PostLike> findByPostIdAndUserId(long postId, long userId);
}
