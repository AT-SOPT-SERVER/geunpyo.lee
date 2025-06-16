package org.sopt.post.repository.dto;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.constant.Tag;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostPageProjection {
	private long postId;
	private String title;
	private String username;
	private String content;
	private List<Tag> tags;

	@QueryProjection
	public PostPageProjection(Long postId, String title, String username, String content, List<Tag> tags) {
		this.postId = postId;
		this.title = title;
		this.username = username;
		this.content = content;
		this.tags = tags;
	}

	public static PostPageProjection from(Post post) {
		return new PostPageProjection(
			post.getId(),
			post.getTitle(),
			post.getUser().getName(),
			post.getContent(),
			post.getTags()
		);
	}

}
