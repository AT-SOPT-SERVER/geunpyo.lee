package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.QPost;
import org.sopt.post.domain.constant.Tag;
import org.sopt.user.domain.QUser;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
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

	private BooleanExpression tagCondition(Tag tag) {
		return tag != null ? QPost.post.tags.contains(tag) : null;
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
