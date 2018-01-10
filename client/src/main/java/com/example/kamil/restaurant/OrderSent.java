package com.example.kamil.restaurant;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kamil.restaurant.DataBase.DishesDataBase;
import com.example.kamil.restaurant.DataBase.OrderDataBase;

import java.util.ArrayList;

public class OrderSent extends Fragment implements View.OnClickListener {

    private static ArrayList<DishesDataBase>orderList=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_order_sent, container, false);
        Button add_to_order = (Button) layout.findViewById(R.id.add_to_order);
        add_to_order.setOnClickListener(this);
        Button rate_dish = (Button) layout.findViewById(R.id.rate_dish);
        rate_dish.setOnClickListener(this);
        Button show_order = (Button) layout.findViewById(R.id.show_order);
        show_order.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.add_to_order:
            onClickAdd(v);
            break;
        case R.id.rate_dish:
            onClickRate(v);
            break;
        case R.id.show_order:
            onClickShowOrder(v);
            break;
        }
    }

    public void onClickAdd(View view) {

        int temp=(int)DetailDish.dishId;
        orderList.add(DishesDataBase.dishes[temp]);
        Toast.makeText(getActivity(),"Dodano do zamówienia: "+DishesDataBase.dishes[temp].getName(), Toast.LENGTH_SHORT).show();
    }

    public void onClickRate(View view) {
        Intent intent = new Intent(getActivity().getApplication(), OrderActivity.class);
        startActivity(intent);
    }

    public void onClickShowOrder(View view) {

        OrderDataBase ord= new OrderDataBase(orderList,1);
        OrderDataBase.orders.add(ord);
        Intent intent = new Intent(getActivity().getApplication(), OrderActivity.class);
        startActivity(intent);

    }


}
