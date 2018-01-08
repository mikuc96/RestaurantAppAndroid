package com.example.waiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.waiter.dummy.OrderContent;

import java.util.Random;

public class WaiterDashboard extends AppCompatActivity implements OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener,
        OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener{

    private ConnectionWithClient clientConnection;
    Button add_meal_btn;
    Button show_tables_btn;
    Random generator = new Random();
    RecyclerViewsManager recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_dashboard);
        bindButtons();
        waitForOrder();
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
                Toast.makeText(getApplicationContext(),	"Dodac aktywnosc stolikow", Toast.LENGTH_SHORT).show();
                //todo Dodac aktywnosc stolikow
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
        OrderCommunicationWithClient clientCom = new OrderCommunicationWithClient();
        clientConnection = new ConnectionWithClient();
        clientConnection.setEventListener(clientCom);
        clientConnection.startListening();
        Toast.makeText(getApplicationContext(),	"waiting for order", Toast.LENGTH_SHORT).show();
    }


}

