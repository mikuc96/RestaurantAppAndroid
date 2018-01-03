package com.example.waiter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.waiter.dummy.DummyContent;

public class WaiterDashboard extends AppCompatActivity implements OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener {

    Button refresh_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_dashboard);
        OrdersWaitingForAcceptionFragment frag = (OrdersWaitingForAcceptionFragment)
                getSupportFragmentManager().findFragmentById(R.id.elements_waiting_for_acceptation);

        refresh_btn = (Button) findViewById(R.id.refresh_tmp_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLists(R.id.elements_waiting_for_acceptation);
                refreshLists(R.id.elements_preparing_in_kitchen);

            }
        });

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        return;
    }

    private void refreshLists(int previousFragment){
        Toast.makeText(getApplicationContext(),	"refreshing", Toast.LENGTH_SHORT).show();

        OrdersWaitingForAcceptionFragment f1 = new OrdersWaitingForAcceptionFragment();
        FragmentTransaction tr1 = getSupportFragmentManager().beginTransaction();
        tr1.replace(previousFragment, f1);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }
}
