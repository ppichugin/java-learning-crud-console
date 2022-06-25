package kz.pichugin.model;

import java.util.List;

/**
 * Main interface that defines what CRUD operations should support application
 */
public interface StorageCrud<T, ID> {
    T save(T type);

    T update(T type);

    T getById(ID id);

    void deleteById(ID id);

    List<T> getAll();
}
