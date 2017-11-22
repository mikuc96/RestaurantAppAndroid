package com.example.waiter;

import android.os.Handler;
import android.os.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * Created by Kamil on 2017-11-21.
 */

public class ConnectionWithClient {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";
    private String mealId = "100"; //id tez bedzie przesylany przez sockety

    Thread m_objThread;
    ServerSocket m_server;
    OrderCommunication orderDisplay;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String clientMsg = msg.obj.toString();
            switch (clientMsg){
                case MAKE_ORDER:
                    orderDisplay.takeOrder(mealId);
                    break;
                case CANCEL_ORDER:
                    orderDisplay.acceptOrderCancellation(mealId);
                    break;
                case ORDER_PROGRESS:
                    orderDisplay.notifyOrderProgress(mealId);
                    break;
                case PAY:
                    orderDisplay.showPaymentNotification(mealId);
                    break;
            }
        }
    };

    void setEventListener(OrderCommunication orderCom) {
        orderDisplay = orderCom;
    }
    void startListening() {
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    m_server=new ServerSocket(2001);
                    Socket connectedSocket =m_server.accept();
                    ObjectInputStream ois =new ObjectInputStream(connectedSocket.getInputStream());
                    ObjectOutputStream oos =new ObjectOutputStream(connectedSocket.getOutputStream());
                    Message clientMessage;
                    for(int i=0 ; i<100; i++) {
                        clientMessage = Message.obtain();
                        clientMessage.obj = ois.readObject();
                        mHandler.sendMessage(clientMessage);
                        oos.writeObject("Waiter: Thank you I got your message: " + clientMessage.obj.toString());
                        sleep(1000);
                    }
                    ois.close();
                    oos.close();
                    m_server.close();
                }
                catch (Exception e)
                {
                    Message msg3= Message.obtain();
                    msg3.obj=e.getMessage();
                    mHandler.sendMessage(msg3);
                }
            }
        });
        m_objThread.start();
        try {
            m_objThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }








}
