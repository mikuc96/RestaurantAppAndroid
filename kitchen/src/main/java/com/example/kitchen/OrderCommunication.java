package com.example.kitchen;

import android.util.Log;

import com.example.kitchen.OrderData.OrderContent;

import java.util.Objects;

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
        int pos = findElementOnList(order[1]);
        OrderContent.processingOrderList.remove(pos);
        tmpReport("Canceling the order "+order[1]);
        return 0;
    }


    @Override
    public int notifyOrderProgress(Integer[] order) {
        tmpReport("Progress: ##% for order: "+order[1]);
        return 0;
    }

    private int findElementOnList(int order_id_int){
        String order_id = String.valueOf(order_id_int);
        int i = 0;
        for(OrderContent.SingleOrder so: OrderContent.processingOrderList)//order[1]
        {
            if(Objects.equals(so.order_id, order_id))
                break;
            i++;
        }
        return i;
    }
}
