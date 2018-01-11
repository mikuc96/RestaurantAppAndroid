package com.example.kamil.restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kamil.restaurant.Dialog.RatingDialog;

public class DetailActivity extends AppCompatActivity{
    public static final String EXTRA_DISH_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailDish detailDish = (DetailDish)
            getFragmentManager().findFragmentById(R.id.detail_frag);
        int workoutId = (int) getIntent().getExtras().get(EXTRA_DISH_ID);
        detailDish.setWorkout(workoutId);


    }
}

