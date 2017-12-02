package com.example.kitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Kitchen extends AppCompatActivity implements MealPreparing{
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
        ConnectionWithWaiter clientComm = new ConnectionWithWaiter();
        clientComm.setEventListener(this);
        clientComm.startListening();
        Toast.makeText(getApplicationContext(),	"waiting for order to prepare", Toast.LENGTH_SHORT).show();
    }


    private void tmpReport(String info){
        serverMessage.setText("" + info);
        Toast.makeText(getApplicationContext(),	"Waiter: " + info, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int startMealPreparing(String mealId) {
        tmpReport("Started meal preparing "+mealId);
        return 0;
    }

    @Override
    public int cancelPreparing(String mealId) {
        tmpReport("Canceling the order "+mealId);
        return 0;
    }

    @Override
    public int notifyOrderProgress(String mealId) {
        tmpReport("Progress: ##% for order: "+mealId);
        return 0;
    }
}
