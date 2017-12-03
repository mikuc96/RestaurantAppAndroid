package com.example.waiter;

import android.os.Handler;
import android.os.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ConnectionWithClient {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";

    private String mealId = "000";

    private Thread m_objThread;
    private ServerSocket client_server;
    private OrderCommunicationWithClient orderDisplay;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] waiterDecodedRequest;
            String clientRqs = msg.obj.toString();
            waiterDecodedRequest = decodeIDsFromClientRequest(clientRqs);
            mealId = waiterDecodedRequest[1];
            switch (waiterDecodedRequest[2]){
                case MAKE_ORDER:
                    try {
                        orderDisplay.takeOrder(mealId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case CANCEL_ORDER:
                    orderDisplay.acceptOrderCancellation(mealId);
                    break;
                case ORDER_PROGRESS:
                    try {
                        orderDisplay.notifyOrderProgress(mealId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case PAY:
                    orderDisplay.showPaymentNotification(mealId);
                    break;
            }
        }
    };

    void setEventListener(OrderCommunicationWithClient orderCom) {
        orderDisplay = orderCom;
    }
    void startListening() {
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    client_server =new ServerSocket(2001);
                    Socket connectedSocket = client_server.accept();
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
                client_server.close();
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


    void sendNotificationToClient(final String notificationMsg) {
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    client_server =new ServerSocket(2001);
                    Socket connectedSocket = client_server.accept();
                    ObjectOutputStream oos =new ObjectOutputStream(connectedSocket.getOutputStream());
                    oos.writeObject("Waiter: Notification for client: " + notificationMsg);
                    sleep(100);

                    oos.close();
                    client_server.close();
                }
                catch (Exception e)
                {
//                    Message msg3= Message.obtain();
//                    msg3.obj=e.getMessage();
                    e.printStackTrace();
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


    String[] decodeIDsFromClientRequest(String msg){
        return msg.split("/");
    }
}
