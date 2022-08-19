package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachineInterface implements VendingInterface {

    Scanner userInputScanner = new Scanner(System.in);
    private TreeMap<String, InventoryItem> inventoryMap = new TreeMap<>();
    CustomerPurse purse = new CustomerPurse();

    //sales log variables
    File salesLog = new File ("SalesLog.txt");
    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss a");

    @Override
    public void getMainMenu() {
        System.out.println("Welcome to Vendo-Matic 800, by Umbrella Crop.");
        System.out.println("[1] Show Inventory");
        System.out.println("[2] Make Purchase");
        System.out.println("[3] Exit");
        System.out.println("Please make a selection: ");
    }

    //Method for reading inventory file and creating the inventory. (part 3)
    @Override
    public void getInventoryMenu(){

        String inventoryFilePath = "vendingmachine.csv";
        File inventoryFile = new File(inventoryFilePath);

        try {
            Scanner inventoryFileScanner = new Scanner(inventoryFile.getAbsoluteFile());

            while(inventoryFileScanner.hasNextLine()){
                String nextLine= inventoryFileScanner.nextLine();
                String[] arrayOfNextLine = nextLine.split("\\|", 0);
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(arrayOfNextLine[2]));
                InventoryItem nextItem = new InventoryItem(arrayOfNextLine[1],price,arrayOfNextLine[3]);
                inventoryMap.put(arrayOfNextLine[0], nextItem);

            }
        }

        catch(FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    // display menu options with their slot, name, price, type and quantity (Part 5)
    @Override
    public void displayMenu() {
        for(Map.Entry m : inventoryMap.entrySet()){
            String slotIdentity = m.getKey().toString();
            InventoryItem currentItem = inventoryMap.get(slotIdentity);
            System.out.print(slotIdentity + " | ");
            System.out.print(currentItem.getItemName() + " | ");
            System.out.print(currentItem.getItemPrice() + " | ");
           System.out.print(currentItem.getItemType() + " | "); // In case we would want item type

            if(currentItem.getQuantity() == 0){
                System.out.println("Sold Out");
            }
            else {
                System.out.println( "Stock " + currentItem.getQuantity());  // Shows item starting quantity (might use later)
            }

        }
        System.out.println();

    }

    @Override
    public void purchaseMenuSelection() {

        System.out.println("[1] Feed Money");
        System.out.println("[2] Select Product");
        System.out.println("[3] Finish Transaction");

    }

    @Override
    public BigDecimal getCurrentFunds() {
        return purse.getPurse();
    }

    @Override
    public void feedMoney(BigDecimal amountAdded) {
        // get starting balance, add money from user, print to logs, display current funds to user
        purse.addFunds(amountAdded);
        BigDecimal endingBalance = purse.getPurse();
        writeFeedMoneyToSalesLog(amountAdded, endingBalance);
        System.out.println("Current funds: $" + purse.getPurse());

    }

    @Override
    public void purchaseItem(String slot) {
        // check if slot is invalid
        if (!inventoryMap.containsKey(slot)) {
            System.out.println("Invalid slot selected.");
        } else {
            // check if item in slot is sold out
            InventoryItem itemSelected = inventoryMap.get(slot);
            int quantity = itemSelected.getQuantity();
            if (quantity == 0) {
                System.out.println("This item is sold out.");
            } else {
                // check if there are insufficient funds
                BigDecimal price = itemSelected.getItemPrice();
                if (price.compareTo(purse.getPurse()) > 0) {
                    System.out.println("The price of the item is greater than the current balance. Please feed money to purchase this item");
                } else {        // item is valid and in stock and user has sufficient funds to purchase

                    // dispense item - update quantity
                    itemSelected.setQuantity(quantity-1);

                    // update user's balance
                    purse.subtractItemCost(price);
                    BigDecimal endingBalance = purse.getPurse();

                    // write to logs
                    writeDispenseItemToSalesLog(price, endingBalance, slot);

                    // print item name, cost, and the money remaining
                    System.out.println("Item purchased: " + itemSelected.getItemName());
                    System.out.println("Item price: " + price);
                    System.out.println("Balance remaining: " + purse.getPurse());

                    // print message for each type of item
                    String type = itemSelected.getItemType();
                    if (type.equals("Chip")) {
                        System.out.println("Crunch Crunch, Crunch!");
                    } else if (type.equals("Candy")) {
                        System.out.println("Munch Munch, Mmm-Good!");
                    } else if (type.equals("Drink")) {
                        System.out.println("Cheers Glug, Glug!");
                    } else if (type.equals("Gum")) {
                        System.out.println("Chew Chew, Pop!");
                    }
                }
            }
        }
    }

    @Override
    public void completeTransaction() {
        // display change
        purse.displayChange();

        // reset balance
        BigDecimal startingBalance = purse.getPurse();
        purse.setBalance(BigDecimal.ZERO);
        BigDecimal endingBalance = purse.getPurse();

        // write to logs
        writeReturnChangeToSalesLog(startingBalance, endingBalance);
    }

    @Override
    public void writeFeedMoneyToSalesLog(BigDecimal amountAdded, BigDecimal endingBalance) {
        try {
            PrintWriter logWriter = new PrintWriter(new FileOutputStream(salesLog.getAbsoluteFile(), true));
            logWriter.append(dateTimeFormat.format(LocalDateTime.now()) + " FEED MONEY: $" + amountAdded + " $" + endingBalance + "\n");
            logWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeDispenseItemToSalesLog(BigDecimal amountSpent, BigDecimal endingBalance, String slot) {
        try {
            PrintWriter logWriter = new PrintWriter(new FileOutputStream(salesLog.getAbsoluteFile(), true));
            InventoryItem item = inventoryMap.get(slot);
            logWriter.append(dateTimeFormat.format(LocalDateTime.now()) + " " + item.getItemName() + " " + slot + " $"  + amountSpent + " $" + endingBalance + "\n");
            logWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeReturnChangeToSalesLog(BigDecimal amountReturned, BigDecimal endingBalance) {
        try {
            PrintWriter logWriter = new PrintWriter(new FileOutputStream(salesLog.getAbsoluteFile(), true));
            logWriter.append(dateTimeFormat.format(LocalDateTime.now()) + " GIVE CHANGE: $" + amountReturned + " $" + endingBalance + "\n");
            logWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getSalesLog() {
        try (Scanner salesLogScanner = new Scanner(salesLog)) {
            while (salesLogScanner.hasNextLine()) {
                String salesLine = salesLogScanner.nextLine();
                System.out.println(salesLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


}
