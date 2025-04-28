package org.sopt.service;

import org.sopt.domain.User;
import org.sopt.dto.UserCreateRequest;
import org.sopt.dto.UserResponse;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponse createUser(UserCreateRequest userCreateRequest) {
		User user = User.create(userCreateRequest.name(), userCreateRequest.email());
		User createdUser = userRepository.save(user);

		return UserResponse.of(createdUser.getName(), createdUser.getEmail());
	}
}
