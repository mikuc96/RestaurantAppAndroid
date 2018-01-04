package com.example.kitchen;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kitchen.dummy.DummyContent;

public class KitchenDashboard extends AppCompatActivity implements MealsProceesingDashboardFragment.OnListFragmentInteractionListener{
    Button refresh_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        refresh_btn = (Button) findViewById(R.id.refresh_tmp_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLists(R.id.elements_in_preparing);
            }
        });
    }

    private void refreshLists(int previousFragment){
        Toast.makeText(getApplicationContext(),	"refreshing", Toast.LENGTH_SHORT).show();

        MealsProceesingDashboardFragment f1 = new MealsProceesingDashboardFragment();
        FragmentTransaction tr1 = getSupportFragmentManager().beginTransaction();
        tr1.replace(previousFragment, f1);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }

    //Todo
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        return ;
    }
}


