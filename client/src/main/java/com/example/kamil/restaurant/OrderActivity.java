package com.example.kamil.restaurant;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class OrderActivity extends Activity {
    private final String MAKE_ORDER = "ORDER";
    private ListView list ;
    private ArrayAdapter<String> adapter ;
    TextView tx_price;
    int price=0;
    private Button erase_order_btn;
    private Button make_order_btn;
    private final String CLIENT_ID = "123456";
    private String mealId = "100";
    private String tableId = "9";

    private OrderDataBase order;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tx_price=(TextView)findViewById(R.id.text_price);
        list = (ListView) findViewById(R.id.listView1);
        erase_order_btn =(Button)findViewById(R.id.but_del_order);
        make_order_btn=(Button)findViewById(R.id.make_order_btn);
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
                SendInfo();
            }
        });
    }

    public void setViewOrder()
    {
        order= OrderDataBase.orders.get(0);
        ArrayList<DishesDataBase>orderListDish=order.getOrder();
        ArrayList<String> view=new ArrayList<String>();
        for(int i=0;i<orderListDish.size();i++) view.add(orderListDish.get(i).getName());

        for(int i=0; i <orderListDish.size();i++) price+=Integer.parseInt(orderListDish.get(i).getPrice());
        String text_price="Do Zapłaty: " + Integer.toString(price);
        tx_price.setText(text_price+"zł");
        adapter = new ArrayAdapter<String>(this, R.layout.text_view_layout,view);
        list.setAdapter(adapter);
    }


    public void SendInfo()
    {
        String orderId = String.valueOf(new Random().nextInt());
        SocketCommunication sc=new SocketCommunication();
        sc.modifyOrder(CLIENT_ID, orderId, mealId, tableId, MAKE_ORDER);

        Toast.makeText(getApplicationContext(),"Zamówienie zostało wysłane, czas oczekiwania około 20min", Toast.LENGTH_SHORT).show();
    }
}
