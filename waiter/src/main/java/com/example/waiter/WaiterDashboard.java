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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class WaiterDashboard extends AppCompatActivity implements OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener,
        OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener{

    private ClientSockets clientConnection;
    private KitchenSockets kitchenConnection;
    private Handler mRefreshingHandler;
    private Handler mConnectionHandler;
    Button add_meal_btn;
    Button show_tables_btn;
    Button refreshBtn;
    Random generator = new Random();
    Runnable runner;
    FirebaseDatabase database;
    public static DatabaseReference menuRef;
    public static DataSnapshot menuSnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_dashboard);
        mRefreshingHandler = new Handler();
        mConnectionHandler = new Handler();
        bindButtons();
        waitForOrder();

        database = FirebaseDatabase.getInstance();
        menuRef = database.getReference("Menu");

        menuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuSnap =dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        runner = new Runnable() {
                @Override
                public void run() {
                refreshRecyclerLists();
                mRefreshingHandler.postDelayed(this, 2000);
            }
        };
    }


    private void bindButtons(){
        refreshRecyclerLists();

        add_meal_btn = (Button) findViewById(R.id.test_add_meal);
        add_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = generator.nextInt(100);
                int orderId = 1;
                OrderContent.addSingleOrderToOrderList(i, i,orderId, i % 5);
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
        return ;
    }


    @Override
    public void onStart(){
        super.onStart();
        autoRefresh();
    }
    @Override
    public void onStop(){
        mRefreshingHandler.removeCallbacks(runner);
        super.onStop();
    }

    @Override
    public void onFragmentInteraction(OrderContent.SingleOrder item) {
        return;
    }


    public void waitForOrder()
    {
        KitchenHandling kitchenCom = new KitchenHandling();
        final ClientHandling clientCom = new ClientHandling();

        mConnectionHandler.post(new Runnable() {
            @Override
            public void run() {

                clientConnection = new ClientSockets();
                clientConnection.setEventListener(clientCom);
                clientConnection.startListening();
            }
        });

        kitchenConnection = new KitchenSockets();
        kitchenConnection.setEventListener(kitchenCom);
        kitchenConnection.startListeningKitchen();
    }

    private void autoRefresh(){
        mRefreshingHandler.postDelayed(runner , 1000);
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

