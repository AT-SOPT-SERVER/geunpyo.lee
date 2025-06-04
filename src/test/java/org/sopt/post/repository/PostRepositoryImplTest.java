package org.sopt.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.global.config.querydsl.QuerydslConfig;
import org.sopt.post.domain.Content;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.Title;
import org.sopt.post.domain.constant.Tag;
import org.sopt.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class})
class PostRepositoryImplTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void setUp() {
		User testUser1 = User.create("김개발", "test@test.com", "test");

		User testUser2 = User.create("바보", "test2@test.com", "test");

		entityManager.persistAndFlush(testUser1);
		entityManager.persistAndFlush(testUser2);

		Tag testTag1 = Tag.BE;
		Tag testTag2 = Tag.DB;

		Post post1 = Post.builder()
			.title(new Title("Spring Boot 개발 가이드"))
			.content(new Content("Spring Boot로 백엔드 개발하기"))
			.user(testUser1)
			.tags(List.of(testTag1))
			.build();

		Post post2 = Post.builder()
			.title(new Title("프론트 개잘하는 법"))
			.content(new Content("서버 잘하면 됨"))
			.user(testUser2)
			.tags(List.of(testTag2))
			.build();

		Post post3 = Post.builder()
			.title(new Title("디자인 개잘하는 법"))
			.content(new Content("서버 잘하면 됨"))
			.user(testUser1)
			.tags(List.of(testTag1))
			.build();

		entityManager.persistAndFlush(post1);
		entityManager.persistAndFlush(post2);
		entityManager.persistAndFlush(post3);
		entityManager.clear();
	}

	@Test
	@DisplayName("키워드와 태그 모두 null인 경우 전체 조회")
	void findByKeywordAndTagDynamically_whenBothNull_shouldReturnAllPosts() {
		// given
		String keyword = null;
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(3);
		assertThat(result).extracting("title")
			.containsExactly(
				"디자인 개잘하는 법",
				"프론트 개잘하는 법",
				"Spring Boot 개발 가이드"
			);
	}

	@Test
	@DisplayName("키워드로 제목 검색")
	void findByKeywordAndTagDynamically_whenKeywordInTitle_shouldReturnMatchingPosts() {
		// given
		String keyword = "Spring";
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle().contains("Spring Boot"));
	}

	@Test
	@DisplayName("키워드로 작성자명 검색")
	void findByKeywordAndTagDynamically_whenKeywordInUserName_shouldReturnMatchingPosts() {
		// given
		String keyword = "김개발";
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(2);
		assertThat(result).extracting("user.name")
			.containsOnly("김개발");
	}

	@Test
	@DisplayName("태그로 검색")
	void findByKeywordAndTagDynamically_whenTagProvided_shouldReturnMatchingPosts() {
		// given
		String keyword = null;
		Tag tag = Tag.BE;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(2);
		assertThat(result).allSatisfy(post ->
			assertThat(post.getTags()).contains(Tag.BE)
		);
	}

	@Test
	@DisplayName("키워드와 태그 조건 모두 적용")
	void findByKeywordAndTagDynamically_whenBothProvided_shouldReturnMatchingPosts() {
		// given
		String keyword = "개발";
		Tag tag = Tag.BE;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(2);
		assertThat(result).allSatisfy(post -> {
			boolean titleContainsKeyword = post.getTitle().contains("개발");
			boolean userNameContainsKeyword = post.getUser().getName().contains("개발");
			assertThat(titleContainsKeyword || userNameContainsKeyword).isTrue();
			assertThat(post.getTags()).contains(Tag.BE);
		});
	}

	@Test
	@DisplayName("대소문자 구분 없이 검색")
	void findByKeywordAndTagDynamically_caseInsensitive_shouldWork() {
		// given
		String keyword = "SPRING";  // 대문자
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle()).containsIgnoringCase("spring");
	}

	@Test
	@DisplayName("빈 문자열 키워드는 null과 같이 처리")
	void findByKeywordAndTagDynamically_whenEmptyKeyword_shouldTreatAsNull() {
		// given
		String keyword = "   ";
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(3);
	}

	@Test
	@DisplayName("존재하지 않는 키워드로 검색")
	void findByKeywordAndTagDynamically_whenKeywordNotExists_shouldReturnEmpty() {
		// given
		String keyword = "존재하지않는키워드";
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("결과가 생성일 기준 내림차순 정렬")
	void findByKeywordAndTagDynamically_shouldOrderByCreatedAtDesc() {
		// given
		String keyword = null;
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).hasSize(3);
		for (int i = 0; i < result.size() - 1; i++) {
			LocalDateTime current = result.get(i).getCreatedAt();
			LocalDateTime next = result.get(i + 1).getCreatedAt();
			assertThat(current).isAfterOrEqualTo(next);
		}
	}

	@Test
	@DisplayName("User가 fetchJoin으로 조회되는지 확인 (N+1 문제 방지)")
	void findByKeywordAndTagDynamically_shouldFetchJoinUser() {
		// given
		String keyword = null;
		Tag tag = null;

		// when
		List<Post> result = postRepository.findByKeywordAndTagDynamically(keyword, tag);

		// then
		assertThat(result).isNotEmpty();

		// entityManager를 clear했음에도 user에 접근 가능해야 함 (fetchJoin 확인)
		entityManager.clear();

		assertThatNoException().isThrownBy(() -> {
			result.forEach(post -> {
				String userName = post.getUser().getName(); // LazyInitializationException 발생하지 않아야 함
				assertThat(userName).isNotNull();
			});
		});
	}
}
