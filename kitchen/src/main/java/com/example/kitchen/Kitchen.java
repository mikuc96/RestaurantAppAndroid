package com.example.kitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Kitchen extends AppCompatActivity {
    Button connectBtn;
    TextView serverMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_activity);
        serverMessage=(TextView)findViewById(R.id.list_order_element);
        connectBtn = (Button) findViewById(R.id.conect_to_waiter);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitForOrder(v);
            }
        });
    }


    public void waitForOrder(View view)
    {
        OrderCommunication oCom = new OrderCommunication();
        ConnectionWithWaiter clientComm = new ConnectionWithWaiter();
        clientComm.setEventListener(oCom);
        clientComm.startListening();
        Toast.makeText(getApplicationContext(),	"waiting for order to prepare", Toast.LENGTH_SHORT).show();
    }

}
