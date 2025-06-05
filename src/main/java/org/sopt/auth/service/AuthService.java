package org.sopt.auth.service;

import org.sopt.auth.controller.request.SignupRequest;
import org.sopt.auth.controller.response.AuthResponse;
import org.sopt.auth.controller.response.UserInfo;
import org.sopt.auth.exception.ConflictEmailException;
import org.sopt.auth.exception.PasswordNotMatchedException;
import org.sopt.auth.service.dto.request.LoginRequest;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.sopt.util.JwtUtil;
import org.sopt.util.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final PasswordUtil passwordUtil;

	@Transactional
	public AuthResponse signup(SignupRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new ConflictEmailException();
		}

		String encodedPassword = passwordUtil.encode(request.password());

		User user = User.create(
			request.name(),
			request.email(),
			encodedPassword
		);

		User savedUser = userRepository.save(user);

		String accessToken = jwtUtil.generateToken(savedUser.getId());

		return AuthResponse.builder()
			.accessToken(accessToken)
			.user(UserInfo.fromEntity(user))
			.build();
	}

	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.email())
			.orElseThrow(UserNotFoundException::new);

		if (!passwordUtil.matches(request.password(), user.getPassword())) {
			throw new PasswordNotMatchedException();
		}

		String accessToken = jwtUtil.generateToken(user.getId());

		return AuthResponse.builder()
			.accessToken(accessToken)
			.user(UserInfo.fromEntity(user))
			.build();
	}
}
