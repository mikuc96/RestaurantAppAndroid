package com.example.waiter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.waiter.OrderData.OrderContent;
import com.example.waiter.OrderData.OrderContent.SingleOrder;

import java.util.ArrayList;
import java.util.List;


public class Tables extends AppCompatActivity {

    Button refresh_btn;
    ImageView table_st[] = new ImageView[6];
    private final int EMPTY = Color.GRAY;
    private final int WAITING_FOR_ORDER = Color.RED;
    private final int EATING = Color.GREEN;
    private final int PAYING = Color.YELLOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refresh_btn = (Button) findViewById(R.id.refresh_table_btn);
        table_st[0] = (ImageView)findViewById(R.id.table_1);
        table_st[1] = (ImageView)findViewById(R.id.table_2);
        table_st[2] = (ImageView)findViewById(R.id.table_3);
        table_st[3] = (ImageView)findViewById(R.id.table_4);
        table_st[4] = (ImageView)findViewById(R.id.table_5);
        table_st[5] = (ImageView)findViewById(R.id.table_6);
        refreshTables();

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTables();
            }
        });
    }

    public void refreshTables(){
        List<SingleOrder> orderList =  OrderContent.processingOrderList;
        for (SingleOrder so: orderList){
            int tableId = Integer.parseInt(so.table_id);
            if(tableId >0 && tableId <=6 ){
                if(so.is_paying){
                    setTableColor(tableId, PAYING);
                }
                else if(so.is_preparing){
                    setTableColor(tableId, WAITING_FOR_ORDER);
                }
                else if(so.is_just_served){
                    setTableColor(tableId, EATING);
                }
            }
            else{
                setTableColor(tableId, EMPTY);
            }
        }
    }
    private void setTableColor(int tableId, int state){
        table_st[tableId-1].setColorFilter(state, PorterDuff.Mode.SRC);
    }
}
