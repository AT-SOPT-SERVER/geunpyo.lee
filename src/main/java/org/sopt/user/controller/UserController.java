package org.sopt.user.controller;

import org.sopt.user.controller.request.UserCreateRequest;
import org.sopt.user.controller.response.UserResponse;
import org.sopt.global.common.dto.ResponseDto;
import org.sopt.user.service.UserService;
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
