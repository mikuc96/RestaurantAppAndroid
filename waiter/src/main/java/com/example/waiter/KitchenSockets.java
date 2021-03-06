package com.example.waiter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.waiter.OrderData.OrderContent;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;


public class KitchenSockets {

    private final String START_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String FINISH_ORDER = "FINISH";
    private final String MORE_TIME = "MORE";
    private final String LESS_TIME = "LESS";
    private final int TIME_UNIT = 300;
    private KitchenHandlingInterface kitchenHandling;
    private ServerSocket kitchen_server;
    private Socket kitchenSocket;


    @SuppressLint("HandlerLeak")
    Handler mKitchenResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] decodedOrd;
            Integer[] order = new Integer[4];

            String kitchenMsg = msg.obj.toString();
            decodedOrd= decodeIDsFromKitchenRequest(kitchenMsg);// [client_id, orderId, mealId, tableId, req_name]
            for( int i = 0; i < 4; i++){
                order[i] = Integer.parseInt(decodedOrd[i]);
                Log.d("el " + String.valueOf(i) + "  ", String.valueOf(order[i]));
            }
            switch (decodedOrd[4]){
                case START_PREPARING:
                    kitchenHandling.startOrderPreparing(order[1]);
                    break;
                case CANCEL_ORDER:
                    kitchenHandling.rejectOrder(order[1]);
                    break;
                case MORE_TIME:
                    kitchenHandling.updateOrderTimer(order[1], TIME_UNIT);
                    break;
                case LESS_TIME:
                    kitchenHandling.updateOrderTimer(order[1], -TIME_UNIT);
                    break;
                case FINISH_ORDER:
                    kitchenHandling.finishOrder(order[1]);
                    break;
            }
        }
    };


    void setEventListener(KitchenHandlingInterface orderCom) {
        kitchenHandling = orderCom;
    }

    public void startListeningKitchen(){
        Thread m_objThreadKitchen = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        kitchen_server = new ServerSocket(2003);
                        Socket connectedSocket = kitchen_server.accept();
                        ObjectInputStream ois = new ObjectInputStream(connectedSocket.getInputStream());
                        Message gotMessage;
                        gotMessage = Message.obtain();
                        gotMessage.obj = ois.readObject();
                        mKitchenResponseHandler.sendMessage(gotMessage);
                        ois.close();
                        kitchen_server.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadKitchen.start();
    }


    public void sendToKitchen(OrderContent.SingleOrder singleOrd, String request_name){
        final String msg = encodeOrder(singleOrd, request_name);
        Thread m_objThreadWaiter = new Thread(new Runnable() {
            public void run() {
                try {
                    kitchenSocket = new Socket("127.0.0.2", 2002);
                    ObjectOutputStream oos = new ObjectOutputStream(kitchenSocket.getOutputStream());
                    oos.writeObject(msg);
                    sleep(1000);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadWaiter.start();
    }


    //[client_id, orderId, mealId, tableId, req_name]
    public String encodeOrder(OrderContent.SingleOrder singleOrd, String request_name){
        return singleOrd.client_id +'/'+ singleOrd.order_id + '/' + singleOrd.meal_id +'/'+ singleOrd.table_id +'/'+request_name;
    }

    String[] decodeIDsFromKitchenRequest(String msg){
        return msg.split("/");
    }
}
