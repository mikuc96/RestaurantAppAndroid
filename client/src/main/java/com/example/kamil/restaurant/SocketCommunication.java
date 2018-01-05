package com.example.kamil.restaurant;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private final String CLIENT_ID = "123456";
    private String mealId = "100";
    TextView serverMessage;
    Thread m_objThreadClient;
    Socket clientSocket;


    private String encodeRequest(String clientID, String mealId, String request_name){
        return clientID +'/'+ mealId +'/'+ request_name;
    }

    public void modifyOrder(final String info_req){

//        final String[] client_requests = {MAKE_ORDER, CANCEL_ORDER, ORDER_PROGRESS, PAY};

        m_objThreadClient=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    clientSocket= new Socket("127.0.0.1",2001);
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    Message waiterResponse;

                        String requestStr = encodeRequest(CLIENT_ID, mealId, info_req);
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
            String serverMsg = msg.obj.toString();
//            Toast.makeText(getApplicationContext(),	"Got message" + serverMsg, Toast.LENGTH_SHORT).show();
            serverMessage.setText(""+msg.obj.toString());
        }
    };

}
