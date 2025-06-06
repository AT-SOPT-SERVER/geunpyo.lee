package org.sopt.post.repository.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDetailDto {
	private Long id;
	private String content;
	private LocalDateTime createdAt;
	private String authorName;
	private Long likesCount;
}