package kz.pichugin.controller;

import kz.pichugin.model.Writer;
import kz.pichugin.service.WriterService;

import java.util.List;

public class WriterController {
    WriterService writerService = new WriterService();

    public Writer save(Writer type) {
        return writerService.save(type);
    }

    public Writer update(Writer type) {
        return writerService.update(type);
    }

    public Writer getById(Long aLong) {
        return writerService.getById(aLong);
    }

    public void deleteById(Long aLong) {
        writerService.deleteById(aLong);
    }

    public List<Writer> getAll() {
        return writerService.getAll();
    }
}
