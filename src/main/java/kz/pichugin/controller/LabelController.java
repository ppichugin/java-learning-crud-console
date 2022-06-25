package kz.pichugin.controller;

import kz.pichugin.model.Label;
import kz.pichugin.model.StorageCrud;
import kz.pichugin.repository.jdbc.LabelRepositoryJdbcImpl;
import kz.pichugin.service.LabelService;

import java.util.List;

public class LabelController implements StorageCrud<Label, Long> {
    private final LabelService labelService;

    public LabelController() {
        labelService = new LabelService(new LabelRepositoryJdbcImpl());
    }

    @Override
    public Label save(Label type) {
        return labelService.save(type);
    }

    @Override
    public Label update(Label type) {
        return labelService.update(type);
    }

    @Override
    public Label getById(Long aLong) {
        return labelService.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        labelService.deleteById(aLong);
    }

    @Override
    public List<Label> getAll() {
        return labelService.getAll();
    }
}