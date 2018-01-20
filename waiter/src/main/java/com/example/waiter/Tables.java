package com.example.waiter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.waiter.OrderData.OrderContent;
import com.example.waiter.OrderData.OrderContent.SingleOrder;

import java.util.List;


public class Tables extends AppCompatActivity {

    private ImageView table_st[] = new ImageView[6];
    private final int EMPTY = Color.GRAY;
    private final int WAITING_FOR_ORDER = Color.YELLOW;
    private final int SERVED = Color.GREEN;
    private final int PAYING = Color.BLUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        table_st[0] = (ImageView)findViewById(R.id.table_1);
        table_st[1] = (ImageView)findViewById(R.id.table_2);
        table_st[2] = (ImageView)findViewById(R.id.table_3);
        table_st[3] = (ImageView)findViewById(R.id.table_4);
        table_st[4] = (ImageView)findViewById(R.id.table_5);
        table_st[5] = (ImageView)findViewById(R.id.table_6);
    }

    @Override
    public void onStart(){
        super.onStart();
        refreshTables();
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
                    setTableColor(tableId, SERVED);
                }
            }
            else{
                setTableColor(tableId, EMPTY);
            }
        }
    }
    private void setTableColor(int tableId, int state){
        try {
            table_st[tableId - 1].setColorFilter(state, PorterDuff.Mode.SRC);
        }catch(ArrayIndexOutOfBoundsException e){
            ;
            }
    }
}
