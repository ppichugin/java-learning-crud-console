package kz.pichugin.controller;

import kz.pichugin.model.StorageCrud;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.jdbc.WriterRepositoryJdbcImpl;
import kz.pichugin.service.WriterService;

import java.util.List;

public class WriterController implements StorageCrud<Writer, Long> {
    private final WriterService writerService;

    public WriterController() {
        writerService = new WriterService(new WriterRepositoryJdbcImpl());
    }

    @Override
    public Writer save(Writer type) {
        return writerService.save(type);
    }

    @Override
    public Writer update(Writer type) {
        return writerService.update(type);
    }

    @Override
    public Writer getById(Long aLong) {
        return writerService.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        writerService.deleteById(aLong);
    }

    @Override
    public List<Writer> getAll() {
        return writerService.getAll();
    }
}
