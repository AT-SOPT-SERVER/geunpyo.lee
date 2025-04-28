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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@CreatedDate
	private LocalDateTime createdAt;

	@Embedded
	private Title title;

	@Embedded
	private Content content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private Post(Title title, User user, Content content) {
		this.title = title;
		this.user = user;
		this.content = content;
	}

	private Post(Title title, Content content) {
		this.title = title;
		this.content = content;
	}

	private Post(Title title) {
		this.title = title;
	}

	protected Post() {
	}

	public static Post create(Title title, Content content) {
		return new Post(title, content);
	}

	public void updatePost(String title) {
		this.title = new Title(title);
	}

	public int getId() {
		return this.id;
	}

	public String getTitleContent() {
		return this.title.content();
	}

	public User getUser() {
		return user;
	}

	public Content getContent() {
		return content;
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

}
