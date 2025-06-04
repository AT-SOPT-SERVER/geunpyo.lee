package org.sopt.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;

	protected User() {
	}

	@Builder
	private User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public static User create(String name, String email, String password) {
		return User.builder()
			.name(name)
			.email(email)
			.password(password)
			.build();
	}

	public long getId() {
		return id;
	}
}
