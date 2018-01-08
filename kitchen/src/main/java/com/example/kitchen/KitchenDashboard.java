package com.example.kitchen;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kitchen.dummy.DummyContent;

public class KitchenDashboard extends AppCompatActivity implements MealsProcessingFragment.OnListFragmentInteractionListener{
    Button refresh_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        waitForOrder();
        bindButtons();
    }

    private void refreshLists(int previousFragment){
        Toast.makeText(getApplicationContext(),	"refreshing", Toast.LENGTH_SHORT).show();

        MealsProcessingFragment f1 = new MealsProcessingFragment();
        FragmentTransaction tr1 = getSupportFragmentManager().beginTransaction();
        tr1.replace(previousFragment, f1);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(getApplicationContext(),	"kliknieto na liste", Toast.LENGTH_SHORT).show();
    }

    public void waitForOrder()
    {
        OrderCommunication oCom = new OrderCommunication();
        ConnectionWithWaiter clientComm = new ConnectionWithWaiter();
        clientComm.setEventListener(oCom);
        clientComm.startListening();
        Toast.makeText(getApplicationContext(),	"waiting for order to prepare", Toast.LENGTH_SHORT).show();
    }
    private void bindButtons(){
        refresh_btn = (Button) findViewById(R.id.refresh_tmp_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLists(R.id.elements_in_preparing);
            }
        });
    }
}


