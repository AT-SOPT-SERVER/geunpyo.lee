package org.sopt.comment.repository;

import java.util.Optional;

import org.sopt.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

	Optional<CommentLike> findByCommentIdAndUserId(long commentId, long userId);
}
