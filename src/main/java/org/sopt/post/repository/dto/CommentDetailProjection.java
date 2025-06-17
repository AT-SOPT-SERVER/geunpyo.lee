package org.sopt.post.repository.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDetailProjection {
	private Long id;
	private String content;
	private LocalDateTime createdAt;
	private String authorName;
	private Long likesCount;

	@QueryProjection
	public CommentDetailProjection(Long id, String content, LocalDateTime createdAt, String authorName,
		Long likesCount) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.authorName = authorName;
		this.likesCount = likesCount;
	}
}