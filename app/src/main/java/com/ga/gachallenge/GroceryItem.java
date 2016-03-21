package com.ga.gachallenge;

/**
 * Created by andrasta on 3/21/16.
 */
public class GroceryItem {
    private String itemName;
    private String itemDescription;
    private int itemQuantity;

    public GroceryItem(String itemName, String itemDescription, int itemQuantity){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName(){
        return itemName;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public int getItemQuantity(){
        return itemQuantity;
    }
}
