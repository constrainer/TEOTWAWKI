package com.example.buzzainerfoodsaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {


    private EditText quantity; //declares variables and which class/data type they belong to or are
    private EditText foodName;
    private EditText expirationDateEditText;
    private EditText storageLocation;
    private Button addToPantry;

    private String channelID = "458";
    private int notificationID = 584;
    private String notifTitle = "Food Notification";
    private String notifContent = "Your ";

    @Override
    protected void onCreate(Bundle savedInstanceState) { //boiler plate when app starts code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Food> foodArray = retrieveFromPrefs();
        String soonToBeExpFood = "";

        for (Food food :  foodArray){
            if(isExpired(food.getExpirationDate(),30)){
                soonToBeExpFood = soonToBeExpFood + " " + food.getFoodName();
            }
        }
        createNotificationChannel();
        NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(notifTitle)
                .setContentText(soonToBeExpFood)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager2.notify(notificationID, builder2.build());

        quantity = findViewById(R.id.quantity);//linking our java variables to our xml objects
        foodName = findViewById(R.id.foodName);
        expirationDateEditText = findViewById(R.id.expirationDate);
        storageLocation = findViewById(R.id.storageLocation);
        addToPantry = findViewById(R.id.addToPantry);

        MyEditTextDatePicker expirationDatePicker = new MyEditTextDatePicker(this, R.id.expirationDate);//constructing a date picker object inputing  our expirationDate variable
        addToPantry.setOnClickListener(new View.OnClickListener() {//setOnClickListener is a function of the Button class? hmmm why is setOnClickListener not start /w caps and OnClickListener does. Maybe OnClickListener is a function of the View class? While setOnClickListener is a constructor?
            @Override
            public void onClick(View v) { //onClick is a function with perameters of the View Class
                if(checkValid()){
                    Food myFood; //if valid we make a new Food object... but how can we use myFood variable before its knows the input?
                    if(storageLocation.getText().toString().matches("")) {//if user hasnt inputed  anything in the storage location create a storage locationless object
                        myFood = new Food(Integer
                                .parseInt(quantity.getText()
                                .toString()),foodName
                                .getText().toString(),convertDateToMilli(expirationDateEditText.
                                getText().toString()));
                    } else { //else make an object with a storage location
                        myFood = new Food(Integer.parseInt(quantity.getText().toString()),foodName.getText().toString(),convertDateToMilli(expirationDateEditText.getText().toString()),storageLocation.getText().toString());
                    }
                    Toast.makeText(getApplicationContext(), myFood.getFoodName()+" Added to Pantry", Toast.LENGTH_LONG).show();//Toast class idk first input 2nd is text and last a duration
                    saveToPreferences(myFood);//saving the myFood object to Preferences
                    quantity.getText().clear();
                    foodName.getText().clear();
                    expirationDateEditText.getText().clear();
                    storageLocation.getText().clear();
                }

            }
        });

    }
    public long convertDateToMilli(String date){ //Our convert data to milli function
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Date object variable sdf
        try {
            Date date1 = sdf.parse(date);//try to set date1 varible to what sdf reads the date as?
            return date1.getTime();//return the d
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isExpired(long milli,int daysToExp){ //function to check if inputed date is in the past
        //Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,daysToExp);
        long currentMilli = cal.getTimeInMillis();
        return milli < currentMilli; //returns true or false
    }

    public boolean checkValid(){
        boolean valid = true;
        String expirationDateText = expirationDateEditText.getText().toString();
        long expirationMilli = convertDateToMilli(expirationDateText);
        if (isExpired(expirationMilli,0)){
            Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (quantity.getText().toString().matches("")){
            quantity.getText().insert(0,"1");
        }
        if (foodName.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(), "Invalid name", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }
    public void saveToPreferences(Food food){
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(food);
        prefsEditor.putString(food.getFoodName(), json);
        prefsEditor.commit();//check w corwin
    }

    //make a function that compares the stored pref dates and returns anything that will expire in
    // the next month? separate functions for expInAMonth, expInAWeek, expInADay or one?


    public ArrayList<Food> retrieveFromPrefs(){
        ArrayList<Food> foodArray = new ArrayList<Food>();
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        Map<String,?> keys = mPrefs.getAll();
        Gson gson = new Gson();
        for(Map.Entry<String,?> key : keys.entrySet()){
            String json = key.getValue().toString();
            Food obj = gson.fromJson(json, Food.class);
            foodArray.add(obj);
        }
        return foodArray;
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "hahaha";
            String description = "mychan";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
