package com.example.waiter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
    private String order_id = "010";
    private String table_id = "001";

    private Thread m_objThread;
    private ServerSocket client_server;
    private ClientHandlingInterface orderDisplay;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] decodedOrd;
            Integer[] order = new Integer[4];
            String clientRqs = msg.obj.toString();
            decodedOrd = decodeIDsFromClientRequest(clientRqs);// [client_id, orderId, mealId, tableId, req_name]

            for( int i = 0; i < 4; i++){
                order[i] = Integer.parseInt(decodedOrd[i]);
                Log.d("el " + String.valueOf(i) + "  ", String.valueOf(order[i]));
            }
//            order = Arrays.copyOfRange(decodedOrd, 0, 3, Integer[].class);
            switch (decodedOrd[4]){ //
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

    void setEventListener(ClientHandlingInterface orderCom) {
        orderDisplay = orderCom;
    }
    void startListening() {
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    Message clientMessage;
                    Socket connectedSocket;
                    client_server =new ServerSocket(2001);
                    connectedSocket = client_server.accept();
                    ObjectInputStream ois =new ObjectInputStream(connectedSocket.getInputStream());;
                    ObjectOutputStream oos =new ObjectOutputStream(connectedSocket.getOutputStream());;
                    for(int i=0 ; i<100000; i++) {
                        clientMessage = Message.obtain();
                        clientMessage.obj = ois.readObject();

                        mHandler.sendMessage(clientMessage);
                        oos.writeObject("Waiter: Thank you I got your message: " + clientMessage.obj.toString());
                        sleep(1000);

                    }
                    ois.close();
                    oos.close();
                    client_server.close();
                    Log.d("TTTTTTTT", "koniec watku m_objThread");

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }
        });
        m_objThread.start();
    }


    void sendNotificationToClient(final String notificationMsg) { //chyba W OGOLE ZLE
        Log.d("sendNotificationToCnt", "sendNotificationToClient");
        m_objThread=new Thread(new Runnable() {
            public void run()
            {
                try {
                    client_server =new ServerSocket(2001); //chyba pomieszane socket z serversocket
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
        String[] code = msg.split("/");
        Log.d("el 0 ", String.valueOf(code[0]));
        Log.d("el 1 ", String.valueOf(code[1]));
        Log.d("el 2 ", String.valueOf(code[2]));
        Log.d("el 3 ", String.valueOf(code[3]));
        Log.d("el 4 ", String.valueOf(code[4]));
        return code;
    }
}
