package org.sopt.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.sopt.post.domain.constant.Tag.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.post.controller.response.PostResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.request.PostCreateCommand;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PostServiceTest {

	@Autowired
	PostService postService;

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@DisplayName("사용자는 게시글을 작성할 수 있다.")
	@Test
	void test() {
		//given
		User user = User.create("test", "test@gmail.com");
		User savedUser = userRepository.save(user);

		PostCreateCommand command = new PostCreateCommand(
			"testtitle",
			"testcontent",
			List.of(BE, ETC)
		);
		//when

		PostResponse response = postService.createPost(savedUser.getId(), command);

		//then
		int postId = response.postId();
		Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

		assertThat(post.getTitle()).isEqualTo(command.title());
		assertThat(post.getContent()).isEqualTo(command.content());
		assertThat(post.getTags()).hasSize(2)
			.contains(BE, ETC);
	}

}
