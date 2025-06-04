package org.sopt.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PasswordUtilTest {

	@Autowired
	private PasswordUtil passwordUtil;

	@DisplayName("비밀번호를 인코딩하면 평문과 달라야 한다.")
	@Test
	void testEncode() {
		//given
		String plainPw = "testPw";

		//when
		String encodedPw = passwordUtil.encode(plainPw);

		//then
		Assertions.assertThat(encodedPw).isNotEqualTo(plainPw);

	}

	@DisplayName("비밀번호를 올바르게 비교 할 수 있어야 한다.")
	@Test
	void testDecode() {
		//given
		String plainPw = "testPw";
		String encodedPw = passwordUtil.encode(plainPw);
		//when

		boolean isMatched = passwordUtil.matches(plainPw, encodedPw);

		//then
		Assertions.assertThat(isMatched).isTrue();

	}

}