package kz.pichugin.repository;

import java.util.List;

public interface CommonRepositoryStrategy<T, ID> {
    T save(T type);

    T update(T type);

    T getById(ID id);

    void deleteById(ID id);

    List<T> getAll();
}