package com.techelevator;

import java.math.BigDecimal;

public class CustomerPurse {

    //initialize constants for change sizes
    final BigDecimal DOLLAR = BigDecimal.valueOf(1.00);
    final BigDecimal QUARTER = BigDecimal.valueOf(.25);
    final BigDecimal DIME = BigDecimal.valueOf(.10);
    final BigDecimal NICKEL = BigDecimal.valueOf(.05);
    final BigDecimal PENNEY = BigDecimal.valueOf(.01);


    //initialize change type quantity variables
    int dollars;
    int quarters;
    int dimes;
    int nickels;
    int pennies;

    BigDecimal purse;

    public BigDecimal getPurse() {
        return purse;
    }

    public CustomerPurse() {
        purse = BigDecimal.valueOf(0.00);
    }

    public void addFunds( BigDecimal customerCashInput) {
        purse = purse.add(customerCashInput);
    }

    public void subtractItemCost(BigDecimal itemCost) {
        purse = purse.subtract(itemCost);
    }

    public void calculateChange() {
        BigDecimal remainingChange = getPurse();

        while (remainingChange.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (remainingChange.compareTo(DOLLAR) > -1) {
                remainingChange = remainingChange.subtract(DOLLAR);
                dollars++;
            } else if (remainingChange.compareTo(QUARTER) > -1) {
                remainingChange = remainingChange.subtract(QUARTER);
                quarters++;
            } else if (remainingChange.compareTo(DIME) > -1) {
                remainingChange = remainingChange.subtract(DIME);
                dimes++;
            } else if (remainingChange.compareTo(NICKEL) > -1) {
                remainingChange = remainingChange.subtract(NICKEL);
                nickels++;
            } else {
                remainingChange = remainingChange.subtract(PENNEY);
                pennies++;
            }
        }
    }

    public void displayChange() {
        calculateChange();
        System.out.println("Your change total is $" + getPurse() + ", which has been dispensed as: ");
        if (dollars > 0) {
            System.out.println(dollars + " dollars");
        }
        if (quarters > 0) {
            System.out.println(quarters + " quarters");
        }
        if (dimes > 0) {
            System.out.println(dimes + " dimes");
        }
        if (nickels > 0) {
            System.out.println(nickels + " nickels");
        }
        if (pennies > 0) {
            System.out.println(pennies + " pennies");
        }

        System.out.println("   ");
        System.out.println("   ");

    }

    public void setBalance(BigDecimal value){
        this.purse = value;
    }
}
