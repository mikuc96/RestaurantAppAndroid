package com.example.waiter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.waiter.OrderData.OrderContent;

import java.util.Random;

public class WaiterDashboard extends AppCompatActivity implements OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener,
        OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener{

    private ClientSockets clientConnection;
    private KitchenSockets kitchenConnection;
    private Handler mHandler;
    Button add_meal_btn;
    Button show_tables_btn;
    Random generator = new Random();
    RecyclerViewsManager recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_dashboard);
        mHandler = new Handler();
        bindButtons();
        waitForOrder();
//        autoRefresh();
    }


    private void bindButtons(){
        recyclerView = new RecyclerViewsManager(this);
        recyclerView.refreshRecyclerLists();

        add_meal_btn = (Button) findViewById(R.id.test_add_meal);
        add_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),	"Adding  meal", Toast.LENGTH_SHORT).show();
                int i = generator.nextInt(10);
                OrderContent.addSingleOrderToOrderList(i, i*10, i + 100, i % 9);
                recyclerView.refreshRecyclerLists();
            }
        });

        show_tables_btn = (Button) findViewById(R.id.tables_btn);
        show_tables_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),	"Dodac aktywnosc stolikow", Toast.LENGTH_SHORT).show();
                //todo Dodac aktywnosc stolikow
                Intent intent = new Intent(getApplicationContext(), Tables.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onListFragmentInteraction(OrderContent.SingleOrder item) {
        recyclerView.refreshRecyclerLists();
    }


    @Override
    public void onFragmentInteraction(OrderContent.SingleOrder item) {
        recyclerView.refreshRecyclerLists();
    }


    public void waitForOrder()
    {
        ClientHandling clientCom = new ClientHandling();
        clientConnection = new ClientSockets();
        clientConnection.setEventListener(clientCom);
        clientConnection.startListening();

        KitchenHandling kitchenCom = new KitchenHandling();
        kitchenConnection = new KitchenSockets();
        kitchenConnection.setEventListener(kitchenCom);
        kitchenConnection.startListeningKitchen();
        Toast.makeText(getApplicationContext(),	"waiting for order", Toast.LENGTH_SHORT).show();
    }

    private void autoRefresh(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                recyclerView.refreshRecyclerLists();
                mHandler.postDelayed(this, 1000);
            }
        });
    }
}

