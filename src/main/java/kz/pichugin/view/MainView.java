package kz.pichugin.view;

import kz.pichugin.util.CheckCommand;
import kz.pichugin.view.factory.LabelViewF;
import kz.pichugin.view.factory.PostViewF;
import kz.pichugin.view.factory.WriterViewF;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView implements View {
    Scanner scanner = new Scanner(System.in);
    boolean isAppRunning = true;

    @Override
    public void run() {
        System.out.println("Console CRUD Application based on MySQL.\n");
        while (isAppRunning) {
            try {
                menu();
                int commandNumber = scanner.nextInt();
                if (!CheckCommand.isRangeCorrectInclusive(commandNumber, 1, 4)) continue;
                switch (commandNumber) {
                    case 1 -> (new WriterViewF()).getView().run();
                    case 2 -> (new PostViewF()).getView().run();
                    case 3 -> (new LabelViewF()).getView().run();
                    case 4 -> isAppRunning = false;
                    default -> System.out.println(CheckCommand.errCommand);
                }
            } catch (InputMismatchException e) {
                System.out.println(CheckCommand.errInputNumber);
            }
        }
    }

    private void menu() {
        System.out.println("--------------------");
        System.out.println("Menu:");
        System.out.println("1. Writers");
        System.out.println("2. Posts");
        System.out.println("3. Labels");
        System.out.println("4. Exit");
        System.out.println("--------------------");
        System.out.print("Enter command (1-4): ");
    }
}
