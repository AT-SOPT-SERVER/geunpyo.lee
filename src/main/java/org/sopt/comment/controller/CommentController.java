package org.sopt.comment.controller;

import org.sopt.comment.controller.request.CommentCreateRequest;
import org.sopt.comment.controller.request.CommentUpdateRequest;
import org.sopt.comment.controller.response.CommentLikeChangeResponse;
import org.sopt.comment.controller.response.CommentResponse;
import org.sopt.comment.service.CommentLikeService;
import org.sopt.comment.service.CommentService;
import org.sopt.global.common.annotation.Auth;
import org.sopt.global.common.dto.ResponseDto;
import org.sopt.user.domain.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final CommentLikeService commentLikeService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseDto<CommentResponse> createComment(@Auth User user,
		@PathVariable Long postId,
		@RequestBody CommentCreateRequest request) {
		return ResponseDto.ok(commentService.createComment(user, postId, null, request.toCommand()));
	}

	@PostMapping("/posts/{postId}/comments/{parentId}/replies")
	public ResponseDto<CommentResponse> createReply(@Auth User user,
		@PathVariable Long postId,
		@PathVariable Long parentId,
		@RequestBody CommentCreateRequest request) {
		return ResponseDto.ok(commentService.createComment(user, postId, parentId, request.toCommand()));
	}

	@PutMapping("/{commentId}")
	public ResponseDto<Void> updateComment(@Auth User user,
		@PathVariable Long commentId,
		@RequestBody CommentUpdateRequest request) {
		commentService.updateComment(user, commentId, request.toCommand());

		return ResponseDto.okWithoutContent();
	}

	@DeleteMapping("/{commentId}")
	public ResponseDto<Void> deleteComment(@Auth User user,
		@PathVariable Long commentId) {
		commentService.deleteComment(user, commentId);
		return ResponseDto.okWithoutContent();
	}

	@PatchMapping("/likes/{commentId}")
	public ResponseDto<CommentLikeChangeResponse> toggleLike(@Auth User user,
		@PathVariable Long commentId) {
		CommentLikeChangeResponse response = commentLikeService.toggleLike(user, commentId);
		return ResponseDto.ok(response);
	}
}
