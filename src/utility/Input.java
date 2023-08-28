package utility;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author xuanbin, Neoh Soon Chee, yong
 */
public class Input {
    private static Scanner s = new Scanner(System.in);

    /**
     * Reinitialises scanner
     */
    public static void reinit() {
        s = new Scanner(System.in);
    }

    /**
     * Prompts the user to enter any string (including blank ones)
     * @param prompt
     * @return the string inputted by user
     */
    public static String getString(String prompt) {
        System.out.print(prompt);
        return s.nextLine();
    }

    /**
     * Prompts the user for a string
     * @param prompt
     * @param allowEmpty Should the input by the user allow empty strings
     * @return
     */
    public static String getString(String prompt, boolean allowEmpty) {
        String res;
        do {
            res = getString(prompt);
            if (res.isBlank() && !allowEmpty) 
                System.out.println("No empty strings are allowed");
        } while (!allowEmpty && res.isBlank());
        return res;
    }
    
    /**
     * Prompts the user `prompt`, returns an integer inputted by user (handles invalid inputs)
     * @param prompt
     * @return
     */
    public static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input: Please input a valid integer");
                s.nextLine(); // consume invalid token
            }
        }
    }

    /**
     * similar to `getInt()`, but limit the user's input to a range
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    public static int getInt(String prompt, int min, int max) {
        while (true) {
            try {
                int res = getInt(prompt);
                if (res >= min && res <= max)
                    return res;
                throw new InputOutOfRangeException(min, max);
            } catch (InputOutOfRangeException iiore) {
                System.out.println(iiore.getMessage());
            }
        }
    }

    /**
     * Prompts the user `prompt`, returns a double inputted by user (handles invalid inputs)
     * @param prompt
     * @return
     */
    public static double getDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return s.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input: Please input a valid decimal");
                s.nextLine(); // consume invalid token
            }
        }
    }

    /**
     * similar to `getInt()`, but limit the user's input to a range
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    public static double getDouble(String prompt, double min, double max) {
        while (true) {
            try {
                double res = getDouble(prompt);
                if (res >= min || res <= max)
                    return res;
                throw new InputOutOfRangeException(min, max);
            } catch (InputOutOfRangeException iiore) {
                System.out.println(iiore.getMessage());
            }
        }
    }

    /**
     * Displays a table the choices available to the user, by transforming the obj through Choicer c
     * @param <T> type of the objs
     * @param choices
     * @param c the function how the obj should be transformed to get a choice
     * @return the choice of the user, in integer (starting from 1)
     */
    public static <T> int getChoice(String prompt, T[] choices, Choicer<T> c) {
        while (true) {
            try {
                String[] newArr = new String[choices.length];
                for (int i = 0; i < choices.length; i++) {
                    newArr[i] = c.toChoiceString(choices[i]);
                }
                TableBuilder tb = new TableBuilder(
                    new String[] {"Choice"}, 
                    new String[][] { newArr }
                );

                tb.printTable(true);

                // handles valid integer validation alrd
                int choice = Input.getInt(prompt);

                if (choice >= newArr.length || choice < 0)
                    throw new InputOutOfRangeException(0, newArr.length - 1);
                
                return choice;
            } catch (InputOutOfRangeException iiore) {
                System.out.println(iiore.getMessage());
            }
        }
    }

    /**
     * Custom exception for out of range inputs
     */
    private static class InputOutOfRangeException extends Exception {
        InputOutOfRangeException(Number min, Number max) {
            super("The given input is out of the range (" + min + " -> " + max + ")");
        }
    }
}
