package kz.pichugin.util;

public class CheckCommand {
    public static String errInputNumber = "Enter the number. Please try again.";
    public static String errCommand = "Error. Select correct command.";
    public static String errIdFormat = "Error. Incorrect ID.";

    public static boolean isRangeCorrectInclusive(int commandNumber, int from, int to) {
        if (commandNumber < from || commandNumber > to) {
            System.out.println(errInputNumber);
            return false;
        }
        return true;
    }
}