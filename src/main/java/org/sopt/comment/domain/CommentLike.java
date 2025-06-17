package org.sopt.comment.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"user_id", "comment_id"})
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class CommentLike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "comment_id", nullable = false)
	private Long commentId;

	private boolean isActive;

	@CreatedDate
	private LocalDateTime createdAt;

	@Builder
	private CommentLike(long userId, long commentId, boolean activeStatus) {
		this.userId = userId;
		this.commentId = commentId;
		this.isActive = activeStatus;
	}

	public static CommentLike create(long userId, long commentId) {
		return CommentLike.builder()
			.userId(userId)
			.commentId(commentId)
			.activeStatus(true)
			.build();
	}

	public void toggle() {
		this.isActive = !this.isActive;
	}
}
