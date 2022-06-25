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
                menu();
                int commandNumber = scanner.nextInt();
                if (!CheckCommand.isRangeCorrectInclusive(commandNumber, 1, 6)) continue;
                switch (commandNumber) {
                    case 1 -> saveWriter();
                    case 2 -> updateWriter();
                    case 3 -> getWriterById();
                    case 4 -> deleteWriterById();
                    case 5 -> getAllWriters();
                    case 6 -> {

                        isAppRunning = false;
                    }
                    default -> System.out.println(CheckCommand.errCommand);
                }
            } catch (InputMismatchException e) {
                System.out.println(CheckCommand.errInputNumber);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void saveWriter() throws IOException {
        while (true) {
            System.out.print("Enter: 'First name' and 'Last name' -- split with whitespace: ");
            Writer writer = getWriterNames(null);
            if (writer == null) continue;
            writerController.save(writer);
            System.out.println("Writer saved!");
            return;
        }
    }

    private Writer getWriterNames(Writer oldWriter) throws IOException {
        String[] params = reader.readLine().trim().split(" ");
        if (params.length < 1 || params.length > 2) {
            System.out.println("Incorrect. Should be First & Last names.");
            return null;
        }
        String firstName = params[0].isEmpty() ? oldWriter.getFirstName() : params[0];
        String lastName = params[1].isEmpty() ? oldWriter.getLastName() : params[1];
        return new Writer(firstName, lastName);
    }

    private void updateWriter() throws IOException {
        Writer writer;
        while (true) {
            System.out.print("Enter writer ID: ");
            long writerId = scanner.nextLong();
            if (writerId < 0) {
                System.out.println(CheckCommand.errIdFormat);
                continue;
            }
            writer = writerController.getById(writerId);
            System.out.println("Current details of writer with id: " + writerId);
            System.out.println(writer);
            break;
        }
        while (true) {
            System.out.print("Enter NEW: 'First name' and 'Last name' -- split with whitespace: ");
            Writer updateWriter = getWriterNames(writer);
            if (updateWriter == null) continue;
            writerController.update(updateWriter);
            System.out.println("Writer updated!");
            return;
        }
    }

    private void getWriterById() {
    }

    private void deleteWriterById() {
    }

    private void getAllWriters() {
        List<Writer> writers = writerController.getAll();
        writers.forEach(System.out::println);
    }

    private void menu() {
        System.out.println("\n");
        System.out.println("Menu for Writers:");
        System.out.println("1. Save new writer");
        System.out.println("2. Update writer");
        System.out.println("3. Get writer by ID");
        System.out.println("4. Delete writer by ID");
        System.out.println("5. Get all writers");
        System.out.println("6. Return");
        System.out.println("--------------------");
        System.out.print("Enter command (1-6): ");
    }
}
