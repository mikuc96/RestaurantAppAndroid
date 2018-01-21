package com.example.waiter;


import android.util.Log;

import com.example.waiter.OrderData.OrderContent;

import static java.lang.Thread.sleep;

public class ClientHandling implements ClientHandlingInterface {


    @Override
    public int takeOrder(Integer[] order) { //[client_id, orderId, mealId, tableId, req_name]
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OrderContent.addSingleOrderToOrderList(order[0], order[1], order[2], order[3]);
        return 0;
    }

    @Override
    public int acceptOrderCancellation(Integer[] order) {
        tmpReport("accepted order cancellation " + String.valueOf(order[2]));
        return 0;
    }

    @Override
    public int notifyOrderProgress(Integer[] order) {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmpReport("Progress: ##% for order: " + String.valueOf(order[2]));
        return 0;
    }

    @Override
    public int showPaymentNotification(Integer[] order) {
        tmpReport("Client want to pay: " + String.valueOf(order[2]));
        for(OrderContent.SingleOrder so: OrderContent.processingOrderList){
            if(so.is_just_served || so.is_prepared) {
                so.is_paying = true;
                so.is_just_served = false;
                so.is_prepared = false;
                so.is_preparing = false;
                break;
            }
        }
        return 0;
    }


    private void tmpReport(String info) {
        Log.d("take order ", info);
    }

}
