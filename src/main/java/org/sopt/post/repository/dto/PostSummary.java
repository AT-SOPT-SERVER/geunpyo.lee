package org.sopt.post.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSummary {
	private Long id;
	private String title;
	private String content;
	private String authorName;
	private Long likesCount;

	@QueryProjection
	public PostSummary(Long id, String title, String content, String authorName, Long likesCount) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.likesCount = likesCount;
	}
}