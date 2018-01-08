package com.example.kamil.restaurant;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class SocketCommunication  {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";

    Thread m_objThreadClient;
    Socket clientSocket;


    private String encodeRequest(String clientID,String orderId, String mealId,String tableId, String request_name){
        return clientID +'/'+ orderId + '/' + mealId +'/'+ tableId +'/'+request_name;
    }

    public void modifyOrder(final String client_id, final String orderId, final String mealId, final String tableId, final String req_name){ // todo na array

        m_objThreadClient=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    clientSocket= new Socket("127.0.0.1",2001);
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    Message waiterResponse;
//                    for in ...
                        String requestStr = encodeRequest(client_id, orderId, mealId, tableId, req_name);
                        oos.writeObject(requestStr);
                        waiterResponse = Message.obtain();
                        waiterResponse.obj = ois.readObject();
                        responseDisplay.sendMessage(waiterResponse);


                    oos.close();
                    ois.close();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        m_objThreadClient.start();
    }



    Handler responseDisplay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("Client respDisplay ", "responseDisplay");
            String serverMsg = msg.obj.toString();
//            Toast.makeText(getApplicationContext(),	"Got message" + serverMsg, Toast.LENGTH_SHORT).show();
        }
    };

}
