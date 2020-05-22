package com.example.buzzainerfoodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;


public class Pantry extends MainActivity {

    private LinearLayout pantryLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);


        pantryLayout = findViewById(R.id.pantryLayout);
        ArrayList<Food> myFood = retrieveFromPrefs();
        Log.d("Earthday","hi");
        ArrayList<TextView> txtViewArray = new ArrayList<>();
        int i = 0;
        for (Food food :  myFood){
            Log.d("u done messed up",food.getFoodName());
            final TextView rowTextView = new TextView(this);

            // set some properties of rowTextView or something
            rowTextView.setText(food.getFoodName());

            // add the textview to the linearlayout
            pantryLayout.addView(rowTextView);

            // save a reference to the textview for later
            txtViewArray.add(i,rowTextView);
            i++;
        }
        txtViewArray.get(0).setText("somestring");
    }




}
