package com.example.buzzainerfoodsaver;

public class Food {
    private int quantity;
    private String foodName;
    private long expirationDate;
    private  String storageLocation;

    Food(int quantity,String foodName,long expirationDate,String storageLocation){
        this.quantity = quantity;
        this.foodName = foodName;
        this.expirationDate = expirationDate;
        this.storageLocation = storageLocation;
    }
    Food(int quantity,String foodName,long expirationDate){
        this.quantity = quantity;
        this.foodName = foodName;
        this.expirationDate = expirationDate;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}


