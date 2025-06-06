package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.constant.Tag;
import org.sopt.post.repository.dto.CommentDetailDto;
import org.sopt.post.repository.dto.PostPageDto;
import org.sopt.post.repository.dto.PostSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
	List<Post> findByKeywordAndTagDynamically(String keyword, Tag tag);

	PostSummaryDto findPostSummary(Long postId);

	List<CommentDetailDto> findCommentDetails(Long postId);

	Page<PostPageDto> search(Pageable pageable);
}
