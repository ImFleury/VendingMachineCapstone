package com.techelevator;


import java.math.BigDecimal;

public class InventoryItem {

    private String itemName;
    private BigDecimal itemPrice;
    private String itemType;
    private int quantity = 5;

    public InventoryItem(String itemName, BigDecimal itemPrice, String itemType) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
