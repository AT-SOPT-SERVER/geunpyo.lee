package org.sopt.comment.domain;

import java.time.LocalDateTime;

import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@CreatedDate
	private LocalDateTime createdAt;

	protected Comment() {
	}

	@Builder
	private Comment(String content, User user, Post post, Comment parent) {
		this.content = content;
		this.user = user;
		this.post = post;
		this.parent = parent;
	}

	public static Comment createWithoutParent(String content, User user, Post post) {
		return Comment.builder()
			.content(content)
			.user(user)
			.post(post)
			.build();
	}

	public static Comment createWithParent(String content, User user, Post post, Comment parent) {
		return Comment.builder()
			.content(content)
			.user(user)
			.post(post)
			.parent(parent)
			.build();
	}

}
