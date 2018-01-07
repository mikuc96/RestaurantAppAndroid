package com.example.kitchen;

import android.util.Log;

public class OrderCommunication implements OrderCommunicationInterface {


    private void tmpReport(String info){
        Log.d("tmpReport", String.valueOf(info));
    }


    @Override
    public int startMealPreparing(String mealId) {
        tmpReport("Started meal preparing "+mealId);
        return 0;
    }


    @Override
    public int cancelPreparing(String mealId) {
        tmpReport("Canceling the order "+mealId);
        return 0;
    }


    @Override
    public int notifyOrderProgress(String mealId) {
        tmpReport("Progress: ##% for order: "+mealId);
        return 0;
    }
}
