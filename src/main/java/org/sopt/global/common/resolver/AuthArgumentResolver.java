package org.sopt.global.common.resolver;

import org.sopt.global.common.annotation.Auth;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.sopt.util.JwtUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtUtil jwtUtil;
	private final UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class) &&
			parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		String authHeader = webRequest.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new NotAuthenticatedException();
		}

		String token = authHeader.substring(7);

		if (!jwtUtil.validateToken(token)) {
			throw new NotAuthenticatedException();
		}

		Long userId = jwtUtil.getUserIdFromToken(token);

		return userService.getUserById(userId);
	}
}
