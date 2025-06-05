package org.sopt.auth.controller;

import org.sopt.auth.controller.request.SignupRequest;
import org.sopt.auth.controller.response.AuthResponse;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.service.dto.request.LoginRequest;
import org.sopt.global.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping
	public ResponseDto<AuthResponse> signup(@RequestBody SignupRequest request) {
		AuthResponse response = authService.signup(request);
		return ResponseDto.ok(response);
	}

	@PostMapping
	public ResponseDto<AuthResponse> login(@RequestBody LoginRequest request) {
		AuthResponse response = authService.login(request);
		return ResponseDto.ok(response);
	}
}
