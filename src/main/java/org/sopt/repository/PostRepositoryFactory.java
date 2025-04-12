package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.repository.storage.FileStorage;
import org.sopt.repository.storage.MarkdownPostStorage;

public class PostRepositoryFactory {

    public static PostRepository createMarkdownRepository(String fileName) {
        FileStorage<Post> storage = new MarkdownPostStorage(fileName);
        return new FilePostRepository(storage);
    }

}
