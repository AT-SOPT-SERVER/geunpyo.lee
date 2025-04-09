package org.example.repository.storage;

import java.util.List;

public interface FileStorage<T> {

    void save(List<T> items);

    List<T> load();
}