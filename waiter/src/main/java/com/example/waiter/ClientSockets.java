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

public class ClientSockets {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";

    private ServerSocket client_server;
    private ClientHandlingInterface orderDisplay;
    private Socket clientSocket;

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
        Thread m_objThreadClient = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Message clientMessage;
                        client_server = new ServerSocket(2001);
                        Socket connectedSocket = client_server.accept();
                        ObjectInputStream ois = new ObjectInputStream(connectedSocket.getInputStream());
                        clientMessage = Message.obtain();
                        clientMessage.obj = ois.readObject();
                        mHandler.sendMessage(clientMessage);
                        ois.close();
                        client_server.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadClient.start();
    }


    public void sendToClient(OrderContent.SingleOrder singleOrd, String request_name){
        final String msg = encodeOrder(singleOrd, request_name);
        Thread m_objThreadWaiter = new Thread(new Runnable() {
            public void run() {
                try {
                    clientSocket = new Socket("127.0.0.2", 2004);
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
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
