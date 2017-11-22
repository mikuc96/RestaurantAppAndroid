package com.example.waiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Waiter extends AppCompatActivity implements OrderCommunication {
    Button connectBtn;
    TextView serverMessage;

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
        tmpReport("Took order"+mealId);
        return 0;
    }

    @Override
    public int acceptOrderCancellation(String mealId) {
        tmpReport("accepted order cancellation"+mealId);
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
}
