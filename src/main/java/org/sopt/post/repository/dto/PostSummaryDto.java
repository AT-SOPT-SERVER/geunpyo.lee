package org.sopt.post.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSummaryDto {
	private Long id;
	private String title;
	private String content;
	private String authorName;
	private Long likesCount;
}