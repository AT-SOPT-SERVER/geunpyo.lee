package org.sopt.post.service;

import org.sopt.comment.repository.CommentRepository;
import org.sopt.post.controller.response.PostLikeChangeResponse;
import org.sopt.post.domain.PostLike;
import org.sopt.post.repository.PostLikeRepository;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {
	private final CommentRepository commentRepository;
	private final PostLikeRepository postLikeRepository;

	@Transactional
	public PostLikeChangeResponse toggleLike(User user, long postId) {
		return postLikeRepository.findByPostIdAndUserId(postId, user.getId())
			.map(postLike -> {
				postLike.toggle();
				return PostLikeChangeResponse.from(postLike);
			})
			.orElseGet(() -> {
				PostLike newLike = PostLike.create(user.getId(), postId);
				postLikeRepository.save(newLike);
				return PostLikeChangeResponse.from(newLike);
			});
	}
}
