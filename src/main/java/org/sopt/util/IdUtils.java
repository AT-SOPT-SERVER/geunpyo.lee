package org.sopt.util;

import java.util.List;
import org.sopt.domain.Post;

public class IdUtils {
    public static int generateNextId(List<Post> posts) {
        return posts.stream()
                .mapToInt(Post::getId)
                .max()
                .orElse(0)
                + 1;
    }
}
