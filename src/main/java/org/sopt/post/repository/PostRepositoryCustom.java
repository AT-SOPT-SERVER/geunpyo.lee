package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.constant.Tag;
import org.sopt.post.repository.dto.CommentDetailDto;
import org.sopt.post.repository.dto.PostSummaryDto;

public interface PostRepositoryCustom {
	List<Post> findByKeywordAndTagDynamically(String keyword, Tag tag);

	PostSummaryDto findPostSummary(Long postId);

	List<CommentDetailDto> findCommentDetails(Long postId);
}
