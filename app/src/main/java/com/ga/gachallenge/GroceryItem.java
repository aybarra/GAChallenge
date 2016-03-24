package com.ga.gachallenge;

/**
 * @author Andy Ybarra
 * Created by Andy Ybarra on 3/21/16.
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

    public void setItemName(String name) {
        this.itemName = name;
    }

    public void setItemDescription(String desc) {
        this.itemDescription = desc;
    }

    public void setItemQuantity(int quant) {
        this.itemQuantity = quant;
    }
}
