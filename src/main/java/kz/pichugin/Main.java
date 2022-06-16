package kz.pichugin;

import kz.pichugin.view.MainView;
import kz.pichugin.view.View;

public class Main {
    public static void main(String[] args) {
        View mainView = new MainView();
        mainView.run();
        System.out.println("Thank you for using our application! Bye!");
    }
}