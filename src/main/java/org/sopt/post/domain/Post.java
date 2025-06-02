package org.sopt.post.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.sopt.post.domain.constant.Tag;
import org.sopt.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	private LocalDateTime createdAt;

	@Embedded
	private Title title;

	@Embedded
	private Content content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(value = EnumType.STRING)
	@ElementCollection(fetch = FetchType.LAZY)
	private List<Tag> tags;

	private long likes;

	@Builder
	private Post(Title title, Content content, List<Tag> tags, User user, long likes) {
		this.title = title;
		this.user = user;
		this.content = content;
		this.tags = tags;
		this.likes = likes;
	}

	private Post(Title title) {
		this.title = title;
	}

	protected Post() {
	}

	public static Post create(Title title, Content content, List<Tag> tags, User user) {
		return Post.builder()
			.title(title)
			.content(content)
			.tags(tags)
			.user(user)
			.likes(0)
			.build();
	}

	public void updatePost(String title, String content) {
		this.title = new Title(title);
		this.content = new Content(content);
	}

	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title.content();
	}

	public User getUser() {
		return user;
	}

	public String getContent() {
		return content.value();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Post post = (Post)o;
		return id == post.id &&
			Objects.equals(title, post.title) &&
			Objects.equals(createdAt, post.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, createdAt);
	}

	public List<Tag> getTags() {
		return tags;
	}

	public long getLikes() {
		return this.likes;
	}
}
