package org.example.util;

import org.example.repository.PostRepository;

public class PostValidator {
    private static final PostRepository postRepository = new PostRepository();

    public static boolean isValidPost(int id) {
        if (!postRepository.isPresent(id)) {
            System.out.println("게시물이 존재하지 않습니다.");
            return false;
        }
        return true;
    }
}
