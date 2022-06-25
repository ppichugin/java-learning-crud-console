package kz.pichugin.service;

import kz.pichugin.model.Label;
import kz.pichugin.model.StorageCrud;
import kz.pichugin.repository.LabelRepository;

import java.util.List;

public class LabelService implements StorageCrud<Label, Long> {
    private final LabelRepository repository;

    public LabelService(LabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Label save(Label type) {
        return repository.save(type);
    }

    @Override
    public Label update(Label type) {
        return repository.update(type);
    }

    @Override
    public Label getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public List<Label> getAll() {
        return repository.getAll();
    }
}