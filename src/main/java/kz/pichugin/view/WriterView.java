package kz.pichugin.view;

import kz.pichugin.controller.WriterController;
import kz.pichugin.model.Writer;
import kz.pichugin.util.CheckCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WriterView implements View {
    Scanner scanner = new Scanner(System.in);
    boolean isAppRunning = true;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    WriterController writerController = new WriterController();

    @Override
    public void run() {
        while (isAppRunning) {
            try {
                int commandNumber = 0;
                menu();
                commandNumber = scanner.nextInt();
                switch (commandNumber) {
                    case 1 -> saveWriter();
                    case 2 -> updateWriter();
                    case 3 -> getWriterById();
                    case 4 -> deleteWriterById();
                    case 5 -> getAllWriters();
                    case 6 -> isAppRunning = false;
                    default -> System.out.println(CheckCommand.ERR_COMMAND);
                }
            } catch (InputMismatchException e) {
                System.out.println(CheckCommand.ERR_INPUT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveWriter() throws IOException {
        while (true) {
            System.out.print("Enter: 'First name' and 'Last name' -- split with whitespace: ");
            Writer writer = getWriterNames();
            if (writer == null) {
                continue;
            }
            writerController.save(writer);
            System.out.println("Writer saved!");
            return;
        }
    }

    private Writer getWriterNames() throws IOException {
        String[] params = reader.readLine().trim().split(" ");
        if (params.length < 1 || params.length > 2 || params[0].isEmpty()) {
            System.out.println(CheckCommand.ERR_INPUT);
            return null;
        }
        return new Writer(params[0], params[1]);
    }

    private void updateWriter() throws IOException {
        Writer writer;
        while (true) {
            System.out.print("Enter writer ID: ");
            long writerId = scanner.nextLong();
            if (writerId < 0) {
                System.out.println(CheckCommand.ERR_ID);
                continue;
            }
            writer = writerController.getById(writerId);
            System.out.println("Current details of writer with id: " + writerId);
            System.out.println(writer);
            break;
        }
        while (true) {
            System.out.print("Enter NEW: 'First name' and 'Last name' -- split with whitespace: ");
            Writer newWriter = getWriterNames();
            if (newWriter == null) continue;
            writer.setFirstName(newWriter.getFirstName());
            writer.setLastName(newWriter.getLastName());
            writerController.update(writer);
            return;
        }
    }

    private void getWriterById() {
        long writerId = getWriterId();
        Writer writer = writerController.getById(writerId);
        System.out.print("Writer details: " + writer);
    }

    private long getWriterId() {
        long writerId;
        while (true) {
            System.out.print("Enter writer ID: ");
            writerId = scanner.nextLong();
            if (writerId < 0) {
                System.out.println(CheckCommand.ERR_ID);
                continue;
            }
            break;
        }
        return writerId;
    }

    private void deleteWriterById() {
        long writerId = getWriterId();
        writerController.deleteById(writerId);
    }

    private void getAllWriters() {
        List<Writer> writers = writerController.getAll();
        writers.forEach(System.out::println);
    }

    private void menu() {
        System.out.println("\n");
        System.out.println(CheckCommand.MENU_SPLITTER);
        System.out.println("Menu for Writers:");
        System.out.println("1. Save new writer");
        System.out.println("2. Update writer");
        System.out.println("3. Get writer by ID");
        System.out.println("4. Delete writer by ID");
        System.out.println("5. Get all writers");
        System.out.println("6. Return");
        System.out.println(CheckCommand.MENU_SPLITTER);
        System.out.print("Enter command (1-6): ");
    }
}
