package com.example.kamil.restaurant;


import android.content.Context;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class WaiterHandling implements WaiterHandlingInterface {

    static Context context;
    WaiterHandling(Context context){
        this.context = context;
    }
    @Override
    public void notifyOrderPrepared(Integer order) {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(context,"Zamówienie gotowe. Za chwilę zostanie dostarczone", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void notifyOrderTime(Integer order) {

    }

    @Override
    public void notifyOrderRejected(Integer order) {

    }
}
