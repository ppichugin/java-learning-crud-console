package kz.pichugin.service;

import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;
import kz.pichugin.repository.jdbc.WriterRepositoryJdbcImpl;

import java.util.List;

public class WriterService {
    WriterRepository repository = WriterRepositoryJdbcImpl.getInstance();

    public void setLabelRepository(WriterRepository repository) {
        this.repository = repository;
    }

    public Writer save(Writer type) {
        return repository.save(type);
    }


    public Writer update(Writer type) {
        return repository.update(type);
    }


    public Writer getById(Long aLong) {
        return repository.getById(aLong);
    }


    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    public List<Writer> getAll() {
        return repository.getAll();
    }
}