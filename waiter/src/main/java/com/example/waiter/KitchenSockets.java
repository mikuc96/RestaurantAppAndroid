package com.example.waiter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.waiter.dummy.OrderContent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;


public class KitchenSockets {
    private final String START_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    Thread m_objThreadKitchen;
    Socket kitchenSocket;

    public void sendOrder(OrderContent.SingleOrder singleOrd, String request_name){
        final String msg = encodeOrder(singleOrd, request_name);
        m_objThreadKitchen=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    kitchenSocket= new Socket("127.0.0.2",2002);
                    ObjectOutputStream oos = new ObjectOutputStream(kitchenSocket.getOutputStream());
                    ObjectInputStream ois =new ObjectInputStream(kitchenSocket.getInputStream());
                    Message serverResponse;

                    oos.writeObject(msg);
                    serverResponse = Message.obtain();
                    serverResponse.obj = ois.readObject();
                    kitchenResponseDisplay.sendMessage(serverResponse);
                    sleep(10000);
                    oos.close();
                    ois.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadKitchen.start();
    }


    @SuppressLint("HandlerLeak")
    Handler kitchenResponseDisplay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            String serverMsg = msg.obj.toString();
    ;
        }
    };

    public String encodeOrder(OrderContent.SingleOrder singleOrd, String request_name){ //[client_id, orderId, mealId, tableId, req_name]
        return singleOrd.client_id +'/'+ singleOrd.order_id + '/' + singleOrd.meal_id +'/'+ singleOrd.table_id +'/'+request_name;
    }

}
