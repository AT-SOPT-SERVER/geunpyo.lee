package org.sopt.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.sopt.domain.constant.Tag;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(value = EnumType.STRING)
	private Tag tag;

	private Post(Title title, User user, Content content, Tag tag) {
		this.title = title;
		this.user = user;
		this.content = content;
		this.tag = tag;
	}

	private Post(Title title, Content content, Tag tag) {
		this.title = title;
		this.content = content;
		this.tag = tag;
	}

	private Post(Title title) {
		this.title = title;
	}

	protected Post() {
	}

	public static Post create(Title title, Content content, Tag tag) {
		return new Post(title, content, tag);
	}

	public void updatePost(String title, String content) {
		this.title = new Title(title);
		this.content = new Content(content);
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

	public Tag getTag() {
		return tag;
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
