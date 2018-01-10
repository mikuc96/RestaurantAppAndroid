package com.example.kitchen;

import android.util.Log;

import com.example.kitchen.dummy.OrderContent;

public class OrderCommunication implements OrderCommunicationInterface {


    private void tmpReport(String info){
        Log.d("tmpReport", String.valueOf(info));
    }


    @Override
    public int startMealPreparing(Integer[] order) {
        tmpReport("Started meal preparing "+order[1]);
        OrderContent.addSingleOrderToOrderList(order[0], order[1], order[2], order[3]);
        return 0;
    }


    @Override
    public int cancelPreparing(Integer[] order) {
        tmpReport("Canceling the order "+order[1]);
        return 0;
    }


    @Override
    public int notifyOrderProgress(Integer[] order) {
        tmpReport("Progress: ##% for order: "+order[1]);
        return 0;
    }
}
