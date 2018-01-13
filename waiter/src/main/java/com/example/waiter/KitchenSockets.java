package com.example.waiter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.waiter.OrderData.OrderContent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;


public class KitchenSockets {
    private final String START_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private KitchenHandlingInterface kitchenHandling;
    private ServerSocket kitchen_server;
    Thread m_objThreadKitchen;
    Socket kitchenSocket;


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
//                case START_MEAL_PREPARING:
//                    kitchenHandling.startMealPreparing(order);
//                    break;
//                case CANCEL_ORDER:
//                    kitchenHandling.cancelPreparing(order);
//                    break;
//                case ORDER_PROGRESS:
//                    kitchenHandling.notifyOrderProgress(order);
//                    break;
            }
        }
    };


    void setEventListener(KitchenHandlingInterface orderCom) {
        kitchenHandling = orderCom;
    }

    public void startListeningKitchen(){
        m_objThreadKitchen=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    kitchen_server = new ServerSocket(2003);
                    Socket connectedSocket = kitchen_server.accept();
                    ObjectInputStream ois =new ObjectInputStream(connectedSocket.getInputStream());
                    Message gotMessage;
                    gotMessage = Message.obtain();
                    gotMessage.obj = ois.readObject();
                    mKitchenResponseHandler.sendMessage(gotMessage);
                    sleep(1000);
                    ois.close();
                    kitchen_server.close();// chyba nie trzeba
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadKitchen.start();
    }


    public void sendToKitchen(OrderContent.SingleOrder singleOrd, String request_name){
        final String msg = encodeOrder(singleOrd, request_name);
        m_objThreadKitchen=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    kitchenSocket= new Socket("127.0.0.2",2002);
                    ObjectOutputStream oos = new ObjectOutputStream(kitchenSocket.getOutputStream());
                    oos.writeObject(msg);
                    sleep(1000);
                    oos.close();
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

    String[] decodeIDsFromKitchenRequest(String msg){
        return msg.split("/");
    }
}
