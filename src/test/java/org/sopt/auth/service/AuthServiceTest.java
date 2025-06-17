package org.sopt.auth.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.auth.controller.request.SignupRequest;
import org.sopt.auth.controller.response.AuthResponse;
import org.sopt.auth.exception.PasswordNotMatchedException;
import org.sopt.auth.service.dto.request.LoginRequest;
import org.sopt.user.domain.User;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;

	@DisplayName("사용자는 회원가입을 할 수 있다.")
	@Test
	void signUpTest() {
		//given
		SignupRequest signupRequest = new SignupRequest(
			"testEmail",
			"testPassword",
			"testUser"
		);
		//when

		AuthResponse response = authService.signup(signupRequest);

		//then
		User user = userRepository.findByEmail(signupRequest.email()).orElseThrow(UserNotFoundException::new);
		assertThat(user.getName()).isEqualTo(signupRequest.name());
		assertThat(user.getPassword()).isNotEqualTo(signupRequest.password());
		assertThat(response.accessToken()).isNotBlank();
	}

	@DisplayName("사용자는 로그인을 할 수 있다.")
	@Test
	void loginTest() {
		//given
		SignupRequest signupRequest = new SignupRequest(
			"testEmail",
			"testPassword",
			"testUser"
		);

		authService.signup(signupRequest);

		//when

		AuthResponse response = authService.login(new LoginRequest(
			"testEmail",
			"testPassword"
		));

		//then
		User user = userRepository.findByEmail("testEmail").orElseThrow(UserNotFoundException::new);
		assertThat(user.getName()).isEqualTo(response.user().name());
	}

	@DisplayName("사용자는 비밀번호를 틀리면 로그인 할 수 없다.")
	@Test
	void loginTestIfWrongPassword() {
		//given
		SignupRequest signupRequest = new SignupRequest(
			"testEmail",
			"testPassword",
			"testUser"
		);

		authService.signup(signupRequest);

		//when & then
		assertThatThrownBy(() -> {
			authService.login(new LoginRequest(
				"testEmail",
				"wrongPassword"
			));
		}).isInstanceOf(PasswordNotMatchedException.class);
	}

}
