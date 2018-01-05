package com.example.waiter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.waiter.dummy.OrderContent;

import java.util.Random;

public class WaiterDashboard extends AppCompatActivity
        implements OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener,
        OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener{

    Button refresh_btn;
    Button add_meal_btn;
    Button show_tables_btn;
    Random generator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_dashboard);

        refresh_btn = (Button) findViewById(R.id.refresh_tmp_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshWaitingForAcceptionList();
                refreshInPreparingList();
            }
        });

        add_meal_btn = (Button) findViewById(R.id.test_add_meal);
        add_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),	"Adding  meal", Toast.LENGTH_SHORT).show();
                int i = generator.nextInt(10);
                OrderContent.addSingleOrderToOrderList(i, i + 100, i % 9);
                refreshWaitingForAcceptionList();
//                refreshList(R.id.elements_waiting_for_acceptation);
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
        Toast.makeText(getApplicationContext(),	"Interaction on waiting for acception meals", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onFragmentInteraction(OrderContent.SingleOrder item) {
        Toast.makeText(getApplicationContext(),	"Interaction on acception meals in processing", Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(getApplicationContext(),	"refreshing", Toast.LENGTH_SHORT).show();
        FragmentTransaction tr1 = getSupportFragmentManager().beginTransaction();
        tr1.replace(previousFragment, newFragment);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }

}

