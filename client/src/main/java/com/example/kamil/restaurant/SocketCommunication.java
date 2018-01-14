package com.example.kamil.restaurant;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class SocketCommunication  {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PREPARED = "PREPARED";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";

    private WaiterHandlingInterface waiterHandling;
    private ServerSocket kitchen_server;

    private Socket clientSocket;

    @SuppressLint("HandlerLeak")
    Handler mWaiterResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] decodedOrd;
            Integer[] order = new Integer[4];

            String kitchenMsg = msg.obj.toString();
            decodedOrd= decodeIDsFromWaiter(kitchenMsg);// [client_id, orderId, mealId, tableId, req_name]
            for( int i = 0; i < 4; i++){
                order[i] = Integer.parseInt(decodedOrd[i]);
                Log.d("el " + String.valueOf(i) + "  ", String.valueOf(order[i]));
            }
            switch (decodedOrd[4]){
                case ORDER_PREPARED:
                    waiterHandling.notifyOrderPrepared(order[1]);
                    break;
                case CANCEL_ORDER:
                    waiterHandling.notifyOrderRejected(order[1]);
                    break;
                case ORDER_PROGRESS:
                    waiterHandling.notifyOrderTime(order[1]);
                    break;
            }
        }
    };


    void setEventListener(WaiterHandlingInterface orderCom) {
        waiterHandling = orderCom;
    }

    public void startListeningWaiter(){
        Thread m_objThreadWaiter = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        kitchen_server = new ServerSocket(2004);
                        Socket connectedSocket = kitchen_server.accept();
                        ObjectInputStream ois = new ObjectInputStream(connectedSocket.getInputStream());
                        Message gotMessage;
                        gotMessage = Message.obtain();
                        gotMessage.obj = ois.readObject();
                        mWaiterResponseHandler.sendMessage(gotMessage);
                        ois.close();
                        kitchen_server.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadWaiter.start();
    }


    private String encodeRequest(String clientID,String orderId, String mealId,String tableId, String request_name){
        return clientID +'/'+ orderId + '/' + mealId +'/'+ tableId +'/'+request_name;
    }

    public void modifyOrder(final String client_id, final String orderId, final String mealId, final String tableId, final String req_name){ // todo na array

        Thread m_objThreadClient = new Thread(new Runnable() {
            public void run() {
                try {
                    clientSocket = new Socket("127.0.0.1", 2001);
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    String requestStr = encodeRequest(client_id, orderId, mealId, tableId, req_name);
                    oos.writeObject(requestStr);
                    sleep(100);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        m_objThreadClient.start();
    }

    String[] decodeIDsFromWaiter(String msg){
        String[] code = msg.split("/");
        Log.d("el 0 ", String.valueOf(code[0]));
        Log.d("el 1 ", String.valueOf(code[1]));
        Log.d("el 2 ", String.valueOf(code[2]));
        Log.d("el 3 ", String.valueOf(code[3]));
        Log.d("el 4 ", String.valueOf(code[4]));
        return code;
    }
}
