package com.example.kitchen;

import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kitchen.OrderData.OrderContent;

import java.util.Random;

public class KitchenDashboard extends AppCompatActivity implements MealsProcessingFragment.OnListFragmentInteractionListener{
    Button refresh_btn;
    Button add_meal;
    Random generator = new Random();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mHandler = new Handler();
        bindButtons();
        refreshLists();
        waitForOrder();
//        autoRefresh();
    }


    @Override
    public void onListFragmentInteraction(OrderContent.SingleOrder item) {
        Toast.makeText(getApplicationContext(),	"kliknieto na liste", Toast.LENGTH_SHORT).show();
    }

    public void waitForOrder()
    {
        OrderCommunication oCom = new OrderCommunication();
        WaiterSockets clientComm = new WaiterSockets();
        clientComm.setEventListener(oCom);
        clientComm.startListeningWaiter();
        Toast.makeText(getApplicationContext(),	"waiting for order to prepare", Toast.LENGTH_SHORT).show();
    }
    private void bindButtons(){
        refresh_btn = (Button) findViewById(R.id.refresh_tmp_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLists();
            }
        });

        add_meal = (Button) findViewById(R.id.add_meal);
        add_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = generator.nextInt(5);
                OrderContent.addSingleOrderToOrderList(i, i*10, i + 100, i % 5);
                refreshLists();
            }
        });
    }

    private void refreshLists(){
        MealsProcessingFragment f1 = new MealsProcessingFragment();
        FragmentTransaction tr1 = getSupportFragmentManager().beginTransaction();
        tr1.replace(R.id.elements_in_preparing, f1);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }

    private void autoRefresh(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshLists();
                mHandler.postDelayed(this, 5000);
            }
        });
        t.start();
    }

}


