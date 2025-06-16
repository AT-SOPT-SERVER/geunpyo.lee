package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.constant.Tag;
import org.sopt.post.repository.dto.CommentDetailProjection;
import org.sopt.post.repository.dto.PostPageProjection;
import org.sopt.post.repository.dto.PostSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
	List<Post> findByKeywordAndTagDynamically(String keyword, Tag tag);

	PostSummaryProjection findPostSummary(Long postId);

	List<CommentDetailProjection> findCommentDetails(Long postId);

	Page<PostPageProjection> search(Pageable pageable);
}
