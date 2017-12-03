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

public class MainActivity extends AppCompatActivity {
    private final String MAKE_ORDER = "ORDER";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";
    private final String PAY = "PAY";
    private final String CLIENT_ID = "123456";
    private String mealId = "100";
    TextView serverMessage;
    Thread m_objThreadClient;
    Socket clientSocket;

    Button makeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverMessage=(TextView)findViewById(R.id.textView_1);

        makeOrder = (Button) findViewById(R.id.make_order_btn);
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyOrder(v);
            }
        });
    }

    private String encodeRequest(String clientID, String mealId, String request_name){
        return clientID +'/'+ mealId +'/'+ request_name;
    }

    public void modifyOrder(View view){
        final String[] client_requests = {MAKE_ORDER, CANCEL_ORDER, ORDER_PROGRESS, PAY};

        m_objThreadClient=new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    clientSocket= new Socket("127.0.0.1",2001);
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    Message waiterResponse;

                    for(String req: client_requests) {
                        String requestStr = encodeRequest(CLIENT_ID, mealId, req);
                        oos.writeObject(requestStr);
                        waiterResponse = Message.obtain();
                        waiterResponse.obj = ois.readObject();
                        responseDisplay.sendMessage(waiterResponse);
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

        m_objThreadClient.start();
    }

//    public void cancelOrder(View view){}

//    public void getOrderProgress(View view){}

    Handler responseDisplay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String serverMsg = msg.obj.toString();
            Toast.makeText(getApplicationContext(),	"Got message" + serverMsg, Toast.LENGTH_SHORT).show();
            serverMessage.setText(""+msg.obj.toString());
        }
    };

}
