package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // creating new vending machine interface
        Scanner scanner = new Scanner(System.in);
        VendingMachineInterface vendingMachine = new VendingMachineInterface();

        //creating the inventory menu from file
        vendingMachine.getInventoryMenu();

        // display the main menu and collecting users option until exit
        int mainMenuOptionSelected = 0;
        do {
            // display main menu
            vendingMachine.getMainMenu();

            try {
                // collect user's selection
                String userInput = scanner.nextLine();
                mainMenuOptionSelected = Integer.parseInt(userInput);

                // execute user's selection
                if (mainMenuOptionSelected == 1) {              // option 1: display menu
                    vendingMachine.displayMenu();
                } else if (mainMenuOptionSelected == 2) {       // option 2: purchase

                    // this should call the purchase menu
                    int purchaseMenuOption = 0;
                    do {
                        try {
                            // display main menu
                            vendingMachine.purchaseMenuSelection();

                            // collect user's selection
                            String purchaseMenuOptionString = scanner.nextLine();
                            purchaseMenuOption = Integer.parseInt(purchaseMenuOptionString);

                            // execute user's selection
                            if (purchaseMenuOption == 1) {          // purchase option 1: feed money

                                // display current funds
                                System.out.println("Current funds: $" + vendingMachine.getCurrentFunds());
                                System.out.println("How much would you like to add (in whole dollars)?");
                                try {
                                    BigDecimal userMoneyValue = BigDecimal.valueOf(Integer.parseInt(scanner.nextLine()));
                                    vendingMachine.feedMoney(userMoneyValue);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid amount entered.");
                                }
                            } else if (purchaseMenuOption == 2) {   // purchase option 2: select product
                                // display menu
                                vendingMachine.displayMenu();

                                // get the slot entered by the user
                                System.out.println("Please input the item number for the item you would like: ");
                                String slotSelected = scanner.nextLine().toUpperCase();

                                // purchase item
                                vendingMachine.purchaseItem(slotSelected);

                            } else if (purchaseMenuOption == 3) {   // purchase option 3: finish transaction
                                // return change
                                vendingMachine.completeTransaction();

                                // reset customer balance to 0
                            } else {
                                System.out.println("Please enter a valid option.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid option.");
                        }

                    } while (purchaseMenuOption != 3);

                }
                else if (mainMenuOptionSelected == 4) {       // option 4: sales logs
                    vendingMachine.getSalesLog();

                } else if (mainMenuOptionSelected != 3) {
                    System.out.println("Please enter a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid option.");
            }

        } while (mainMenuOptionSelected != 3);

        System.out.println("Thank you for choosing Umbrella Corp.");
    }
}
