package org.sopt.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.domain.CommentLike;
import org.sopt.comment.repository.CommentLikeRepository;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.comment.service.CommentLikeService;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CommentLikeServiceConcurrencyTest {

	@Autowired
	private CommentLikeService commentLikeService;

	@Autowired
	private CommentLikeRepository commentLikeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	private User testUser;
	private Comment testComment;

	@BeforeEach
	void setUp() {
		commentLikeRepository.deleteAll();
		testUser = userRepository.save(User.builder().name("testUser").build());
		testComment = commentRepository.save(Comment.builder().content("test comment").build());
	}

	@Test
	@DisplayName("한 사용자가 동시에 같은 댓글에 좋아요를 여러 번 눌러도 하나만 생성되어야 한다")
	void concurrentCommentLikeTest() throws InterruptedException {
		int threadCount = 20;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger constraintViolationCount = new AtomicInteger(0);
		AtomicInteger otherExceptionCount = new AtomicInteger(0);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					commentLikeService.toggleLike(testUser, testComment.getId());
					successCount.incrementAndGet();
				} catch (DataIntegrityViolationException e) {
					constraintViolationCount.incrementAndGet();
				} catch (Exception e) {
					otherExceptionCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(10, TimeUnit.SECONDS);
		executorService.shutdown();

		List<CommentLike> commentLikes = commentLikeRepository.findAll();

		assertThat(commentLikes).hasSizeLessThanOrEqualTo(1);

		assertThat(constraintViolationCount.get()).isGreaterThan(0);

		assertThat(successCount.get() + constraintViolationCount.get() + otherExceptionCount.get())
			.isEqualTo(threadCount);
	}
}
