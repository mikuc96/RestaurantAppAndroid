package com.example.waiter;


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
        return i;
    }
}
