package org.example.repository;

import org.example.domain.Post;
import org.example.repository.storage.FileStorage;
import org.example.repository.storage.MarkdownPostStorage;

public class PostRepositoryFactory {

    public static PostRepository createMarkdownRepository(String fileName) {
        FileStorage<Post> storage = new MarkdownPostStorage(fileName);
        return new FilePostRepository(storage);
    }
    
}
