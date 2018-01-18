package com.example.kitchen;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kitchen.OrderData.OrderContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class KitchenDashboard extends AppCompatActivity implements MealsProcessingFragment.OnListFragmentInteractionListener{
    Button refresh_btn;
    Button add_meal;
    Random generator = new Random();
    Runnable runner;
    private Handler mHandler;
    private Handler mRefreshingHandler;
    FirebaseDatabase database;
    public static DatabaseReference userRef;
    public static DataSnapshot userSnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mHandler = new Handler();
        mRefreshingHandler = new Handler();
        bindButtons();
        waitForOrder();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Menu");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userSnap=dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        runner = new Runnable() {
            @Override
            public void run() {
                refreshLists();
                mRefreshingHandler.postDelayed(this, 5000);
            }
        };
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
        mRefreshingHandler.postDelayed(runner , 5000);
    }

}


