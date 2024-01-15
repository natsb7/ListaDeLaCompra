package com.nataliasep.listadelacompra.Models;

public class ItemList {
    private String itemName;
    private String img;
    private int isPurchased;

    public ItemList() {
    }

    public ItemList(String itemName, String img, int isPurchased) {
        this.itemName = itemName;
        this.img = img;
        this.isPurchased = isPurchased;
    }

    public String getItem() {
        return itemName;
    }

    public String getImg() {
        return img;
    }
    public int getPurchased() {
        return isPurchased;
    }

    public void setPurchased(int purchased) {
        this.isPurchased = purchased;
    }

}
