package kz.pichugin.service;

import kz.pichugin.model.StorageCrud;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;

import java.util.List;

public class WriterService implements StorageCrud<Writer, Long> {
    private final WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Writer save(Writer type) {
        return repository.save(type);
    }

    @Override
    public Writer update(Writer type) {
        return repository.update(type);
    }

    @Override
    public Writer getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public List<Writer> getAll() {
        return repository.getAll();
    }
}