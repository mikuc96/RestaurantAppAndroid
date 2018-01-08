package com.example.waiter;


import android.util.Log;
import com.example.waiter.dummy.OrderContent;
import static java.lang.Thread.sleep;

public class OrderCommunicationWithClient implements OrderCommunicationWithClientInterface {



    @Override
    public int takeOrder(Integer[] order) { //[client_id, orderId, mealId, tableId, req_name]
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("WWWWWWWWWW","wwwwwwww");
        OrderContent.addSingleOrderToOrderList(order[0], order[1], order[2], order[3]);
//        tmpReport("Took order " + String.valueOf(order[2]));
//        clientComm.sendNotificationToClient("Took order " + mealId);
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
//        clientComm.sendNotificationToClient("Progress: ##% for order: " + mealId);
        return 0;
    }

    @Override
    public int showPaymentNotification(Integer[] order) {
        tmpReport("Client want to pay: " + String.valueOf(order[2]));
        return 0;
    }


    private void tmpReport(String info) {
//        Toast.makeText(getApplicationContext(),	"Waiter: " + info, Toast.LENGTH_SHORT).show();
        Log.d("take order ", info);
    }
}
