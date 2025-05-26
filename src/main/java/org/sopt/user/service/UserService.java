package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.controller.request.UserCreateRequest;
import org.sopt.user.controller.response.UserResponse;
import org.sopt.user.repository.UserRepository;
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

		return UserResponse.of(createdUser.getId(), createdUser.getName(), createdUser.getEmail());
	}
}
