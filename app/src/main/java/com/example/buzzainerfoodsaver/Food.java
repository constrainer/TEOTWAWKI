package com.example.buzzainerfoodsaver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Food implements Comparable<Food> {
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
        return this.foodName;
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
    @Override
    public int compareTo(Food foodComp){
        int food1 = Math.toIntExact(this.expirationDate/1000);
        int food2 = Math.toIntExact(foodComp.getExpirationDate()/1000);
        this.getExpirationDate();

        return food1 - food2;

    }
    public String formatDate(){
        Date newD = new Date(this.expirationDate);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = dateFormat.format(newD);
        return strDate;
    }
}


