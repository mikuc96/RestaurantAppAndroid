package com.example.waiter;


import android.util.Log;

import com.example.waiter.OrderData.OrderContent;
import com.example.waiter.OrderData.OrderContent.SingleOrder;

import java.util.Objects;


public class KitchenHandling implements KitchenHandlingInterface {


    @Override
    public void rejectOrder(Integer order) {
        int pos = findElementOnList(order);
        OrderContent.processingOrderList.remove(pos);
    }

    @Override
    public void updateOrderTimer(Integer order, int timeUnit) {
        int pos = findElementOnList(order);
        OrderContent.processingOrderList.get(pos).timer += timeUnit;
    }

    @Override
    public void finishOrder(Integer order) {
        int pos = findElementOnList(order);
        OrderContent.processingOrderList.get(pos).is_prepared = true;
        OrderContent.processingOrderList.get(pos).is_preparing = false;
        OrderContent.processingOrderList.get(pos).timer = 0;
    }

    @Override
    public void startOrderPreparing(Integer order) {
        int pos = findElementOnList(order);
        OrderContent.processingOrderList.get(pos).is_preparing = true;
    }

    private int findElementOnList(int order_id_int){
        String order_id = String.valueOf(order_id_int);
        int i = 0;
        for(SingleOrder so: OrderContent.processingOrderList)
        {
            if(Objects.equals(so.order_id, order_id))
                break;
            i++;
        }
        Log.d("Position of upgrad el", String.valueOf(i));
        return i;
    }
}
