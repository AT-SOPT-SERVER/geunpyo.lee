package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.constant.Tag;

public interface PostRepositoryCustom {
	List<Post> findByKeywordAndTagDynamically(String keyword, Tag tag);
}
