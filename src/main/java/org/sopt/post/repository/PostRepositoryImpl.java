package org.sopt.post.repository;

import static org.sopt.post.domain.QPost.*;
import static org.sopt.user.domain.QUser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.sopt.comment.domain.QComment;
import org.sopt.comment.domain.QCommentLike;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.QPost;
import org.sopt.post.domain.QPostLike;
import org.sopt.post.domain.constant.Tag;
import org.sopt.post.repository.dto.CommentDetailDto;
import org.sopt.post.repository.dto.PostPageDto;
import org.sopt.post.repository.dto.PostSummaryDto;
import org.sopt.user.domain.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Post> findByKeywordAndTagDynamically(String keyword, Tag tag) {
		QPost post = QPost.post;
		QUser user = QUser.user;

		return queryFactory
			.selectFrom(post)
			.leftJoin(post.user, user).fetchJoin()
			.where(
				keywordCondition(keyword),
				tagCondition(tag)
			)
			.orderBy(post.createdAt.desc())
			.fetch();
	}

	@Override
	public PostSummaryDto findPostSummary(Long postId) {
		QPost post = QPost.post;
		QUser user = QUser.user;
		QPostLike postLike = QPostLike.postLike;

		return queryFactory
			.select(Projections.constructor(PostSummaryDto.class,
				post.id,
				post.title.content,
				post.content.value,
				user.name,
				postLike.id.countDistinct().coalesce(0L)
			))
			.from(post)
			.join(post.user, user)
			.leftJoin(postLike).on(
				postLike.postId.eq(post.id)
					.and(postLike.active.eq(true))
			)
			.where(post.id.eq(postId))
			.groupBy(post.id, user.id)
			.fetchOne();
	}

	@Override
	public List<CommentDetailDto> findCommentDetails(Long postId) {
		QComment comment = QComment.comment;
		QUser user = QUser.user;
		QCommentLike commentLike = QCommentLike.commentLike;

		return queryFactory
			.select(Projections.constructor(CommentDetailDto.class,
				comment.id,
				comment.content,
				comment.createdAt,
				user.name,
				commentLike.id.countDistinct().coalesce(0L)
			))
			.from(comment)
			.join(comment.user, user)
			.leftJoin(commentLike).on(
				commentLike.commentId.eq(comment.id)
					.and(commentLike.isActive.eq(true))
			)
			.where(comment.post.id.eq(postId))
			.groupBy(comment.id, user.id)
			.orderBy(comment.createdAt.asc())
			.fetch();
	}

	@Override
	public Page<PostPageDto> search(Pageable pageable) {

		List<Post> posts = queryFactory
			.selectFrom(post)
			.join(post.user, user).fetchJoin()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(post.createdAt.desc())
			.fetch();

		List<PostPageDto> results = posts.stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());

		JPAQuery<Long> countQuery = queryFactory
			.select(post.count())
			.from(post);

		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}

	private PostPageDto convertToDto(Post post) {
		return new PostPageDto(
			post.getId(),
			post.getTitle(),
			post.getUser().getName(),
			post.getContent(),
			post.getTags()
		);
	}

	private Map<Long, List<Tag>> getTagsForPosts(Set<Long> postIds) {
		QPost qPost = QPost.post;

		List<Tuple> tagResults = queryFactory
			.select(qPost.id, qPost.tags)
			.from(qPost)
			.where(qPost.id.in(postIds))
			.fetch();

		Map<Long, List<Tag>> tagsMap = new HashMap<>();
		for (Tuple tuple : tagResults) {
			Long postId = tuple.get(qPost.id);
			List<Tag> tags = tuple.get(qPost.tags);

			tagsMap.computeIfAbsent(postId, k -> new ArrayList<>()).addAll(tags);
		}

		return tagsMap;
	}

	private BooleanExpression tagCondition(Tag tag) {
		return tag != null ? post.tags.contains(tag) : null;
	}

	private BooleanExpression keywordCondition(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return null;
		}

		QPost post = QPost.post;

		return post.title.content.containsIgnoreCase(keyword)
			.or(post.user.name.containsIgnoreCase(keyword));
	}

}
