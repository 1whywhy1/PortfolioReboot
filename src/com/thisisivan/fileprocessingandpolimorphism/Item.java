package com.thisisivan.fileprocessingandpolimorphism;

/**
 * Item class extends Sellable
 * @author srao3
 */
public class Item extends Sellable {
    private String itemName;
    private float itemCost;
    private float profitMargin;

    public Item()
    {
        itemCost=0f;
        itemName="noname";
        profitMargin = 0f;        
    }
    public Item(String itemName) {
        this.itemName = itemName;
    }

    public Item(String itemName, float itemCost) {
        this.itemName = itemName;
        this.itemCost = itemCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemCost() {
        return itemCost;
    }

    public void setItemCost(float itemCost) {
        this.itemCost = itemCost;
    }

    public float getProfitMargin() {
        return profitMargin;
    }

    @Override
    public void setProfitMargin() {
        this.profitMargin = 0.25f;
    }
    
    @Override
    public String toString()
    {
        String temp = "";
        temp = this.itemName+";";
        temp += String.valueOf(itemCost)+";";
        temp += String.valueOf(profitMargin);
        
        return temp;
    }
    
}
