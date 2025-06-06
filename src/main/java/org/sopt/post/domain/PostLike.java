package org.sopt.post.domain;

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
	@UniqueConstraint(columnNames = {"user_id", "post_id"})
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PostLike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "postId", nullable = false)
	private Long postId;

	private boolean isActive;

	@CreatedDate
	private LocalDateTime createdAt;

	@Builder
	private PostLike(long userId, long postId, boolean activeStatus) {
		this.userId = userId;
		this.postId = postId;
		this.isActive = activeStatus;
	}

	public static PostLike create(long userId, long postId) {
		return PostLike.builder()
			.userId(userId)
			.postId(postId)
			.activeStatus(true)
			.build();
	}

	public void toggle() {
		this.isActive = !this.isActive;
	}
}
