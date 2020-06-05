package com.example.buzzainerfoodsaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class Pantry extends MainActivity {
    private Button removeFoodButn;
    private LinearLayout pantryLayout;

    final ArrayList<Food> compost = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);




        removeFoodButn = findViewById(R.id.removeFoodBtn);
        pantryLayout = findViewById(R.id.pantryLayout);
        refreshPantry();

        removeFoodButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tossInCompost(compost);
            }
        });
    }
private void tossInCompost(ArrayList<Food> compost){
    SharedPreferences  mPrefs = getSharedPreferences("myData",MODE_PRIVATE);
    SharedPreferences.Editor prefsEditor = mPrefs.edit();
    for (Food food : compost){
        prefsEditor.remove(food.getFoodName());
    }
    prefsEditor.commit();
    refreshPantry();
}
private void refreshPantry(){
    if (pantryLayout.getChildCount() > 0) {
        pantryLayout.removeAllViews();
    }

        ArrayList<Food> myFood = retrieveFromPrefs();
        Collections.sort(myFood);
        ArrayList<TextView> txtViewArray = new ArrayList<>();
        int i = 0;
        for (final Food food :  myFood){
            final TextView rowTextView = new TextView(this);

            // set some properties of rowTextView or something
            rowTextView.setText(food.getFoodName() + " expires " + food.formatDate());
            rowTextView.setTextColor(Color.parseColor("#000000"));
            rowTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("googy", rowTextView.getHighlightColor() + " ");
                    if (rowTextView.getCurrentTextColor() == Color.parseColor("#FF0000")) {
                        compost.remove(food);
                        rowTextView.setTextColor(Color.parseColor("#000000"));
                    }else{
                        compost.add(food);
                        rowTextView.setTextColor(Color.parseColor("#FF0000"));
                    }

                }
            });

            // add the textview to the linearlayout
            pantryLayout.addView(rowTextView);

            // save a reference to the textview for later
            txtViewArray.add(i,rowTextView);
            i++;
        }
    }



}
