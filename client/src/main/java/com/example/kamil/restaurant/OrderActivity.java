package com.example.kamil.restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamil.restaurant.DataBase.DishesDataBase;
import com.example.kamil.restaurant.DataBase.OrderDataBase;

import java.util.ArrayList;
import java.util.Random;

public class OrderActivity extends Activity {
    private final String MAKE_ORDER = "ORDER";
    private ListView list ;
    private ArrayAdapter<String> adapter ;
    TextView tx_price;
    int price=0;
    ArrayList<DishesDataBase>orderListDish;
    private Button erase_order_btn;
    private Button make_order_btn;
    private final String CLIENT_ID = "123456";
    private String mealId = "100";
    private String tableId = "2";
    Handler mConnectionHandler;
    SocketCommunication clientConnection;

    private OrderDataBase order;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tx_price=(TextView)findViewById(R.id.text_price);
        list = (ListView) findViewById(R.id.listView1);
        erase_order_btn =(Button)findViewById(R.id.but_del_order);
        make_order_btn=(Button)findViewById(R.id.make_order_btn);
        mConnectionHandler = new Handler();
        setViewOrder();
        erase_order_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OrderDataBase.orders.get(0).eraseOrderList();
                price=0;
                Toast.makeText(getApplicationContext(),"Lista dań została skasowana", Toast.LENGTH_SHORT).show();
                setViewOrder();
            }
        });

        make_order_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendInfo();
            }
        });
        waitForOrder();
    }

    public void setViewOrder()
    {
        order= OrderDataBase.orders.get(0);
        orderListDish=order.getOrder();
        ArrayList<String> view=new ArrayList<String>();
        for(int i=0;i<orderListDish.size();i++) view.add(orderListDish.get(i).getName());

        for(int i=0; i <orderListDish.size();i++) price+=Integer.parseInt(orderListDish.get(i).getPrice());
        String text_price="Do Zapłaty: " + Integer.toString(price);
        tx_price.setText(text_price+"zł");
        adapter = new ArrayAdapter<String>(this, R.layout.text_view_layout,view);
        list.setAdapter(adapter);
    }


    public void sendInfo()
    {
        String mealId = "100";
        String orderId = String.valueOf(new Random().nextInt());
        SocketCommunication sc=new SocketCommunication();
        ArrayList<DishesDataBase> temp= order.getOrder();

        for(DishesDataBase i:temp)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sc.modifyOrder(CLIENT_ID, orderId, i.getId(), tableId, MAKE_ORDER);

        }
        Toast.makeText(getApplicationContext(),"Zamówienie zostało wysłane, czas oczekiwania około 20min", Toast.LENGTH_SHORT).show();

    }

    public void waitForOrder()
    {
        final WaiterHandling waiterCom = new WaiterHandling(OrderActivity.this);
        mConnectionHandler.post(new Runnable() {
            @Override
            public void run() {
            clientConnection = new SocketCommunication();
            clientConnection.setEventListener(waiterCom);
            clientConnection.startListeningWaiter();
            }
        });
}
}
