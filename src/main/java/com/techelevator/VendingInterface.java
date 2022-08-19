package com.techelevator;

import java.math.BigDecimal;

public interface VendingInterface {

    void getMainMenu();

    void getInventoryMenu();

    void purchaseMenuSelection();

    void displayMenu();

    void feedMoney(BigDecimal amountAdded);

    void purchaseItem(String slot);

    BigDecimal getCurrentFunds();

    void completeTransaction();

    void writeFeedMoneyToSalesLog(BigDecimal amountAdded, BigDecimal endingBalance);

    void writeDispenseItemToSalesLog(BigDecimal amountSpent, BigDecimal endingBalance, String slot);

    void writeReturnChangeToSalesLog(BigDecimal amountReturned, BigDecimal endingBalance);

    void getSalesLog();

}
