package com.example.kamil.restaurant.DataBase;

import java.util.ArrayList;

/**
 * Created by mikuc on 1/5/18.
 */

public class OrderDataBase {

    private ArrayList<DishesDataBase> orderList = new ArrayList<DishesDataBase>();
    private int id = 0;

    public static final ArrayList<OrderDataBase> orders = new ArrayList<OrderDataBase>();

    public OrderDataBase(ArrayList<DishesDataBase> db, int id) {
        this.orderList = db;
        this.id = id;
    }

    public void addOrder() {
        orders.add(new OrderDataBase(orderList, id));
    }

    public ArrayList<DishesDataBase> getOrder() {
        return orderList;
    }

    public void eraseOrderList() {
        orderList.clear();
    }

}