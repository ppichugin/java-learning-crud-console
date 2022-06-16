package kz.pichugin.service;

import kz.pichugin.model.Label;
import kz.pichugin.repository.LabelRepository;
import kz.pichugin.repository.jdbc.LabelRepositoryJdbcImpl;

import java.util.List;

public class LabelService {
    LabelRepository repository = LabelRepositoryJdbcImpl.getInstance();

    public void setLabelRepository(LabelRepository repository) {
        this.repository = repository;
    }

    public Label save(Label type) {
        return repository.save(type);
    }

    public Label update(Label type) {
        return repository.update(type);
    }

    public Label getById(Long aLong) {
        return repository.getById(aLong);
    }

    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    public List<Label> getAll() {
        return repository.getAll();
    }
}