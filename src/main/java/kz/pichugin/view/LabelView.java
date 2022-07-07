package kz.pichugin.view;

import kz.pichugin.controller.LabelController;
import kz.pichugin.model.Label;
import kz.pichugin.util.CheckCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelView implements View {
    Scanner scanner = new Scanner(System.in);
    boolean isAppRunning = true;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final LabelController labelController = new LabelController();

    @Override
    public void run() {
        while (isAppRunning) {
            try {
                int commandNumber;
                menu();
                commandNumber = scanner.nextInt();
                switch (commandNumber) {
                    case 1 -> saveLabelView();
                    case 2 -> updateLabelView();
                    case 3 -> getByIdLabelView();
                    case 4 -> deleteByIdLabelView();
                    case 5 -> getAllLabelView();
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

    private void saveLabelView() throws IOException {
        System.out.print("Input label name: ");
        String name = reader.readLine();
        System.out.print("Input post ID: ");
        Long postId = scanner.nextLong();
        Label label = new Label(null, name, postId);
        label = labelController.save(label);
        System.out.print("Added: ");
        getLabelToString(label);
    }

    private void updateLabelView() throws IOException {
        System.out.print("Select label ID: ");
        Long id = scanner.nextLong();
        System.out.print("Select new label name: ");
        String name = reader.readLine();
        Label label = labelController.update(new Label(id, name));
        System.out.print("Updated: ");
        getLabelToString(label);
    }

    private void getAllLabelView() {
        List<Label> labelList = labelController.getAll();
        labelList.forEach(this::getLabelToString);
    }

    private void deleteByIdLabelView() {
        System.out.print("Input label ID: ");
        Long id = scanner.nextLong();
        labelController.deleteById(id);
    }

    private void getByIdLabelView() {
        System.out.print("Input label ID: ");
        Long id = scanner.nextLong();
        Label label = labelController.getById(id);
        if (label == null) {
            System.out.println("ID not found, try again");
        } else {
            getLabelToString(label);
        }
    }

    private void menu() {
        System.out.println(CheckCommand.MENU_SPLITTER);
        System.out.println("Menu for Labels:");
        System.out.println("1. Save label");
        System.out.println("2. Update label");
        System.out.println("3. Get label by ID");
        System.out.println("4. Delete label by ID");
        System.out.println("5. Get all labels");
        System.out.println("6. Return");
        System.out.println(CheckCommand.MENU_SPLITTER);
        System.out.print("Enter command (1-6): ");
    }

    private void getLabelToString(Label label) {
        StringBuilder sb = new StringBuilder("Label: ");
        sb.append("id=").append(label.getId());
        sb.append(", name=").append(label.getName());
        sb.append(" , postId=").append(label.getPostId());
        System.out.println(sb);
    }
}