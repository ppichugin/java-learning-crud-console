package kz.pichugin.controller;

import kz.pichugin.model.StorageCrud;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.jdbc.WriterRepositoryJdbcImpl;
import kz.pichugin.service.WriterService;
import kz.pichugin.util.CheckCommand;

import java.util.List;

public class WriterController implements StorageCrud<Writer, Long> {
    private final WriterService writerService;

    public WriterController() {
        writerService = new WriterService(new WriterRepositoryJdbcImpl());
    }

    @Override
    public Writer save(Writer writer) {
        return writerService.save(writer);
    }

    @Override
    public Writer update(Writer writer) {
        if (writer == null) {
            return null;
        }
        if (writer.isNew()) {
            System.out.println("Writer is new, can not be updated.");
            return null;
        }
        Writer writerUpdated = writerService.update(writer);
        if (writerUpdated != null) {
            System.out.println("Writer updated.");
            System.out.println(writerUpdated);
        } else {
            System.out.println(CheckCommand.ERR_ID);
            System.out.println("Writer not updated");
        }
        return writerUpdated;
    }

    @Override
    public Writer getById(Long id) {
        Writer writer = writerService.getById(id);
        if (writer == null) {
            System.out.println(CheckCommand.MENU_SPLITTER);
            System.out.println("!!! Writer not found !!! ");
            System.out.println(CheckCommand.MENU_SPLITTER);
        }
        return writer;
    }

    @Override
    public void deleteById(Long id) {
        writerService.deleteById(id);
    }

    @Override
    public List<Writer> getAll() {
        return writerService.getAll();
    }
}
