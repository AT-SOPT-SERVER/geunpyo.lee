package org.sopt.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@CreatedDate
	private LocalDateTime createdAt;

	@Embedded
	private Title title;

	public Post(String title) {
		this.title = new Title(title);
	}

	public Post(int id, String title, LocalDateTime createdAt) {
		this.id = id;
		this.title = new Title(title);
		this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
	}

	public Post() {

	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title.getContent();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void updatePost(String title) {
		this.title = new Title(title);
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

	@Override
	public String toString() {
		return "Post{" +
			"id=" + id +
			", title='" + title + '\'' +
			", createdAt=" + createdAt +
			'}';
	}
}
