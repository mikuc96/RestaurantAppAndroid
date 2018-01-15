package com.example.waiter;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.waiter.OrderData.OrderContent;

import java.util.Random;

public class WaiterDashboard extends AppCompatActivity implements OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener,
        OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener{

    private ClientSockets clientConnection;
    private KitchenSockets kitchenConnection;
    private Handler mHandler;
    private Handler mConnectionHandler;
    private Thread refreshThread;
    Button add_meal_btn;
    Button show_tables_btn;
    Button refreshBtn;
    Random generator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_dashboard);
        mHandler = new Handler();
        mConnectionHandler = new Handler();
        bindButtons();
        waitForOrder();
//        autoRefresh();
    }


    private void bindButtons(){
        refreshRecyclerLists();

        add_meal_btn = (Button) findViewById(R.id.test_add_meal);
        add_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = generator.nextInt(5);
                OrderContent.addSingleOrderToOrderList(i, i*10, i + 100, i % 5);
                refreshRecyclerLists();
            }
        });

        show_tables_btn = (Button) findViewById(R.id.tables_btn);
        show_tables_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tables.class);
                startActivity(intent);
            }
        });
        refreshBtn = (Button) findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refreshRecyclerLists();
            }
        });
    }


    @Override
    public void onListFragmentInteraction(OrderContent.SingleOrder item) {
        refreshRecyclerLists();
    }


    @Override
    public void onStart(){
        super.onStart();
//        autoRefresh();
    }
    @Override
    public void onStop(){
        super.onStop();
//        if(refreshThread != null)
//            refreshThread.interrupt();
    }

    @Override
    public void onFragmentInteraction(OrderContent.SingleOrder item) {
        refreshRecyclerLists();
    }


    public void waitForOrder()
    {
        KitchenHandling kitchenCom = new KitchenHandling();
        final ClientHandling clientCom = new ClientHandling();

//        clientConnection = new ClientSockets();
//        clientConnection.setEventListener(clientCom);
//        clientConnection.startListening();
//
        mConnectionHandler.post(new Runnable() {
            @Override
            public void run() {

                clientConnection = new ClientSockets();
                clientConnection.setEventListener(clientCom);
                clientConnection.startListening();
            }
        });
//        t.start();

        kitchenConnection = new KitchenSockets();
        kitchenConnection.setEventListener(kitchenCom);
        kitchenConnection.startListeningKitchen();
    }

    private void autoRefresh(){
        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshRecyclerLists();
                mHandler.postDelayed(this, 5000);
            }
        });
        refreshThread.start();
    }

    private void refreshRecyclerLists() {
        refreshWaitingForAcceptionList();
        refreshInPreparingList();
    }

    private void refreshWaitingForAcceptionList(){
        OrdersWaitingForAcceptionFragment f1 = new OrdersWaitingForAcceptionFragment();
        replaceFrgments(R.id.elements_waiting_for_acceptation, f1);
    }

    private void refreshInPreparingList(){
        OrdersInPreparingFragment f1 = new OrdersInPreparingFragment();
        replaceFrgments(R.id.elements_preparing_in_kitchen, f1);
    }

    private void replaceFrgments(int previousFragment, Fragment newFragment){
        FragmentTransaction tr1 = getSupportFragmentManager().beginTransaction();
        tr1.replace(previousFragment, newFragment);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }
}

