package com.example.waiter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class ConnectionWithClient {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";

    private String mealId = "000";
    private String order_id = "010";
    private String table_id = "001";

    private Thread m_objThread;
    private ServerSocket client_server;
    private OrderCommunicationWithClientInterface orderDisplay;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] decodedOrd;
            Integer[] order;
            String clientRqs = msg.obj.toString();
            decodedOrd = decodeIDsFromClientRequest(clientRqs);// [client_id, orderId, mealId, tableId, req_name]
            order = Arrays.copyOfRange(decodedOrd, 0, 4, Integer[].class);
            switch (decodedOrd[4]){
                case MAKE_ORDER:
                    try {
                        orderDisplay.takeOrder(order);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case CANCEL_ORDER:
                    orderDisplay.acceptOrderCancellation(order);
                    break;
                case ORDER_PROGRESS:
                    try {
                        orderDisplay.notifyOrderProgress(order);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case PAY:
                    orderDisplay.showPaymentNotification(order);
                    break;
            }
        }
    };

    void setEventListener(OrderCommunicationWithClientInterface orderCom) {
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
                    e.printStackTrace();
                }
            }
        });
        m_objThread.start();
    }


    void sendNotificationToClient(final String notificationMsg) {
        Log.d("sendNotificationToCnt", "sendNotificationToClient");
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
