package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserById(long userId) {
		return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
	}
}
