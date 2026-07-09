package Display;

import java.util.Scanner;

/**
 * start menu and welcomes the players to Animal Chess.
 * 
 * @author Zachary Y Tan
 * @version 1.0
 */
public class StartMenu {

    /**
     * Displays the ASCII art menu and loops until the user enters "Start".
     */
    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("______________________________________________________________");
        System.out.println("|                                                             |");
        System.out.println("|     /\\_/\\                                     <:3 )~~       |");
        System.out.println("|    ( o o )                                                  |");
        System.out.println("|     > ^ <                                                   |");
        System.out.println("|                  WELCOME TO ANIMAL CHESS                    |");
        System.out.println("|                                                             |");
        System.out.println("|             ENTER: \"Start\" to begin the game                |");
        System.out.println("|                                                             |");
        System.out.println("______________________________________________________________");

        // Loop until the player types "Start" (case-insensitive)
        while (!input.equalsIgnoreCase("Start")) {
            System.out.print("\n> ");
            input = scanner.nextLine().trim();
            
            if (!input.equalsIgnoreCase("Start")) {
                System.out.println("Invalid command. Please type \"Start\" to play!");
            }
        }
        
        System.out.println("\nInitializing board... Good luck!\n");
    }
}
