package com.example.waiter;

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

public class Waiter extends AppCompatActivity implements OrderCommunication {
    private final String START_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private String mealId = "100"; //id tez bedzie przesylany przez sockety
    Button connectBtn;
    TextView serverMessage;

    Thread m_objThreadKitchen;
    Socket kitchenSocket;
    Button sendOrderToKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);
        serverMessage=(TextView)findViewById(R.id.textView_1);
        connectBtn = (Button) findViewById(R.id.connect_btn);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitForOrder(v);
            }
        });

        sendOrderToKitchen = (Button) findViewById(R.id.order_to_kitchen_btn);
        sendOrderToKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrdrerToKitchen(v);
            }
        });
    }

    public void waitForOrder(View view)
    {
        ConnectionWithClient clientComm = new ConnectionWithClient();
        clientComm.setEventListener(this);
        clientComm.startListening();
        Toast.makeText(getApplicationContext(),	"waiting for order", Toast.LENGTH_SHORT).show();
    }

    private void tmpReport(String info){
        serverMessage.setText("" + info);
        Toast.makeText(getApplicationContext(),	"Waiter: " + info, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int takeOrder(String mealId) {
        tmpReport("Took order "+mealId);
        return 0;
    }

    @Override
    public int acceptOrderCancellation(String mealId) {
        tmpReport("accepted order cancellation "+mealId);
        return 0;
    }

    @Override
    public int notifyOrderProgress(String mealId) {
        tmpReport("Progress: ##% for order: "+mealId);
        return 0;
    }

    @Override
    public int showPaymentNotification(String mealId) {
        tmpReport("Client want to pay: "+mealId);
        return 0;
    }


// KITCHEN COMMUNICATION:


    public void sendOrdrerToKitchen(View view){
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
//                    oos.writeObject(mealId);

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
                // TODO Auto-generated catch block
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
            Toast.makeText(getApplicationContext(),	"Got message" + serverMsg, Toast.LENGTH_SHORT).show();
            serverMessage.setText(""+msg.obj.toString());
        }
    };
}
