package com.example.kitchen;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.kitchen.dummy.OrderContent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ConnectionWithWaiter {
    private final String START_MEAL_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private String mealId = "000"; //id tez bedzie przesylany przez sockety

    private Thread m_objThread;
    private ServerSocket waiter_server;
    private Socket waiterSocket;
    private OrderCommunicationInterface mealStatusDisplay;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] decodedOrd;
            Integer[] order = new Integer[4];

            String waiterRequest = msg.obj.toString();
            decodedOrd= decodeIDsFromClientRequest(waiterRequest);// [client_id, orderId, mealId, tableId, req_name]
            for( int i = 0; i < 4; i++){
                order[i] = Integer.parseInt(decodedOrd[i]);
                Log.d("el " + String.valueOf(i) + "  ", String.valueOf(order[i]));
            }
            switch (decodedOrd[4]){
                case START_MEAL_PREPARING:
                    mealStatusDisplay.startMealPreparing(order);
                    break;
                case CANCEL_ORDER:
                    mealStatusDisplay.cancelPreparing(order);
                    break;
                case ORDER_PROGRESS:
                    mealStatusDisplay.notifyOrderProgress(order);
                    break;
            }
        }
    };

    void setEventListener(OrderCommunicationInterface orderCom) {
        mealStatusDisplay = orderCom;
    }
    void startListeningWaiter() {
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    waiter_server =new ServerSocket(2002);
                    Socket connectedSocket = waiter_server.accept();
                    ObjectInputStream ois =new ObjectInputStream(connectedSocket.getInputStream());
                    Message gotMessage;
                    for(int i=0 ; i<100; i++) {
                        gotMessage = Message.obtain();
                        gotMessage.obj = ois.readObject();
                        mHandler.sendMessage(gotMessage);
                        sleep(1000);
                    }
                    ois.close();
                    waiter_server.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        m_objThread.start();
    }


    void sendToWaiter(OrderContent.SingleOrder singleOrd, String request_name) {
        final String msg = encodeOrder(singleOrd, request_name);
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    waiterSocket =new Socket("127.0.0.2",2003); // czy ip ok?
                    ObjectOutputStream oos =new ObjectOutputStream(waiterSocket.getOutputStream());
                    oos.writeObject(msg);
                    sleep(1000);
                    oos.close();
//                    waiter_server.close(); // chyba nie trzeba Nie a zamykać
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        m_objThread.start();
    }

    public String encodeOrder(OrderContent.SingleOrder singleOrd, String request_name){ //[client_id, orderId, mealId, tableId, req_name]
        return singleOrd.client_id +'/'+ singleOrd.order_id + '/' + singleOrd.meal_id +'/'+ singleOrd.table_id +'/'+request_name;
    }

    String[] decodeIDsFromClientRequest(String msg){
        return msg.split("/");
    }
}

