package org.sopt.controller;

import org.sopt.dto.UserCreateRequest;
import org.sopt.dto.UserResponse;
import org.sopt.global.common.dto.ResponseDto;
import org.sopt.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseDto<UserResponse> create(@RequestBody UserCreateRequest dto) {
		UserResponse response = userService.createUser(dto);
		return ResponseDto.created(response);
	}
}
