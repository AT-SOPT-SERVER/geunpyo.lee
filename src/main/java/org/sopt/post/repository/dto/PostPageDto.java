package org.sopt.post.repository.dto;

import java.util.List;

import org.sopt.post.domain.constant.Tag;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PostPageDto {
	long postId;
	String title;
	String username;
	String content;
	List<Tag> tags;

	@QueryProjection
	public PostPageDto(Long postId, String title, String username, String content, List<Tag> tags) {
		this.postId = postId;
		this.title = title;
		this.username = username;
		this.content = content;
		this.tags = tags;
	}

}
