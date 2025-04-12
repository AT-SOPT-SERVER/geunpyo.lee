package org.sopt.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sopt.domain.Post;
import org.sopt.repository.storage.FileStorage;

public class FilePostRepository implements PostRepository {
    private final Map<Integer, Post> postMap = new HashMap<>();
    private final FileStorage<Post> fileStorage;

    public FilePostRepository(FileStorage<Post> fileStorage) {
        this.fileStorage = fileStorage;
        loadFromStorage();
    }

    @Override
    public Post save(Post post) {
        postMap.put(post.getId(), post);
        saveToStorage();
        return post;
    }

    @Override
    public Optional<Post> findById(int id) {
        return Optional.ofNullable(postMap.get(id));
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(postMap.values());
    }

    @Override
    public void update(Post post) {
        int idToUpdate = post.getId();
        if (postMap.containsKey(idToUpdate)) {
            postMap.put(idToUpdate, post);
            saveToStorage();
        }
    }

    @Override
    public boolean delete(int id) {
        if (postMap.containsKey(id)) {
            postMap.remove(id);
            saveToStorage();
            return true;
        }
        return false;
    }

    @Override
    public List<Post> findByTitleContaining(String titleKeyword) {
        if (titleKeyword == null || titleKeyword.isEmpty()) {
            return findAll();
        }

        return postMap.values().stream()
                .filter(post -> post.getTitle().contains(titleKeyword))
                .toList();
    }

    @Override
    public boolean isPresent(int id) {
        return postMap.get(id) != null;
    }

    private void loadFromStorage() {
        postMap.clear();

        List<Post> posts = fileStorage.load();
        for (Post post : posts) {
            postMap.put(post.getId(), post);
        }
    }

    private void saveToStorage() {
        fileStorage.save(new ArrayList<>(postMap.values()));
    }
}
