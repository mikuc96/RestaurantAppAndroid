package com.example.waiter;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;


public class ConnectionWithKitchen {
    private final String START_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    Thread m_objThreadKitchen;
    Socket kitchenSocket;

    public void sendOrdrerToKitchen(){
        final String[] waiterReqests = {START_PREPARING, CANCEL_ORDER, ORDER_PROGRESS};

        m_objThreadKitchen=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    kitchenSocket= new Socket("127.0.0.2",2002);
                    ObjectOutputStream oos = new ObjectOutputStream(kitchenSocket.getOutputStream());
                    ObjectInputStream ois =new ObjectInputStream(kitchenSocket.getInputStream());
                    Message serverResponse;

                    for(String req: waiterReqests) {
                        oos.writeObject(req);
                        serverResponse = Message.obtain();
                        serverResponse.obj = ois.readObject();
                        kitchenResponseDisplay.sendMessage(serverResponse);
                        sleep(10000);
                    }
                    oos.close();
                    ois.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        m_objThreadKitchen.start();
    }


    Handler kitchenResponseDisplay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String serverMsg = msg.obj.toString();
//            Toast.makeText(getApplicationContext(),	"Got message" + serverMsg, Toast.LENGTH_SHORT).show();
//            serverMessage.setText(""+msg.obj.toString());
        }
    };

}
