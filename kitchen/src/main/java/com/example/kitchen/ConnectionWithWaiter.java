package com.example.kitchen;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

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
    private OrderCommunicationInterface mealStatusDisplay;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] waiterDecodedRequest = new String[3];

            String waiterRequest = msg.obj.toString();
            waiterDecodedRequest= decodeIDsFromClientRequest(waiterRequest);
            mealId = waiterDecodedRequest[1];
            switch (waiterDecodedRequest[2]){
                case START_MEAL_PREPARING:
                    mealStatusDisplay.startMealPreparing(mealId);
                    break;
                case CANCEL_ORDER:
                    mealStatusDisplay.cancelPreparing(mealId);
                    break;
                case ORDER_PROGRESS:
                    mealStatusDisplay.notifyOrderProgress(mealId);
                    break;
            }
        }
    };

    void setEventListener(OrderCommunicationInterface orderCom) {
        mealStatusDisplay = orderCom;
    }
    void startListening() {
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    waiter_server =new ServerSocket(2002);
                    Socket connectedSocket = waiter_server.accept();
                    ObjectInputStream ois =new ObjectInputStream(connectedSocket.getInputStream());
                    ObjectOutputStream oos =new ObjectOutputStream(connectedSocket.getOutputStream());
                    Message waiterMessage;
                    for(int i=0 ; i<100; i++) {
                        waiterMessage = Message.obtain();
                        waiterMessage.obj = ois.readObject();
                        mHandler.sendMessage(waiterMessage);
                        oos.writeObject("Kitchen: Thank you I got your message: " + waiterMessage.obj.toString());
                        sleep(1000);
                    }
                    ois.close();
                    oos.close();
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
    String[] decodeIDsFromClientRequest(String msg){
        return msg.split("/");
    }
}

