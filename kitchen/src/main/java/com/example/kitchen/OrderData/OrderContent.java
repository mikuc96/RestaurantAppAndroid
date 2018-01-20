package com.example.kitchen.OrderData;

import com.example.kitchen.KitchenDashboard;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderContent {

    public static final List<SingleOrder> processingOrderList = new ArrayList<SingleOrder>();
    public static final Map<String, SingleOrder> processingOrderMap = new HashMap<String, SingleOrder>();


    public static class DatabaseResponse{
        String name;
        int time;

        public DatabaseResponse(DataSnapshot ds){
            name = ds.child("name").getValue().toString();
            time = Integer.parseInt(ds.child("time").getValue().toString());
        }
    }

    public static DatabaseResponse getFromDatabase(int meal_id) {
        String mealId = String.valueOf(meal_id);
        if (KitchenDashboard.userSnap.child(mealId).exists()) {
            DatabaseResponse dr = new DatabaseResponse(KitchenDashboard.userSnap.child(mealId));
            return dr;
        }
        return null;
    }

    public static void addSingleOrderToOrderList(int client_id, int order_id, int meal_id, int table_id) {
        SingleOrder item = createSingleOrderData(client_id, order_id , meal_id, table_id);
        processingOrderList.add(item);
        processingOrderMap.put(String.valueOf(item.order_id), item);
    }

    public static void updateTimer(int position){
        SingleOrder el = processingOrderList.get(position);
        if(el.is_preparing && !el.is_prepared)
            el.timer -= 1;
    }

    private static SingleOrder createSingleOrderData(int client_id, int order_id, int meal_id, int table_id) {
        return new SingleOrder(client_id, order_id, meal_id, getRecipe(order_id), table_id);
    }

    private static String getRecipe(int position) {
        return "Przepis";
    }


    public static class SingleOrder {
        public final String order_id;
        public String meal_name = "Jeszcze Glupsza Nazwa Potrawy"; //final
        public String meal_id = "111";
        public String client_id = "111";
        public final String recipe;
        public final String table_id;
        public Integer timer;
        public Boolean is_paying;
        public Boolean is_accepted_by_waiter;
        public Boolean is_prepared;
        public Boolean is_preparing;
        public Boolean was_served;
        public Boolean is_rejected_by_kitchen;


        public SingleOrder(int client_id, int order_id, int meal_id, String recipe, int table_id) {
            DatabaseResponse dr = getFromDatabase(meal_id);
            this.order_id = String.valueOf(order_id);
            this.table_id = String.valueOf(table_id);
            this.meal_id = String.valueOf(meal_id);
            this.client_id = String.valueOf(client_id);
            if(meal_id > 0 )
                this.meal_name = dr.name;
            this.recipe = recipe;
            this.timer = dr.time;
            this.is_paying = false;
            this.is_accepted_by_waiter = false;
            this.is_prepared = false;
            this.is_preparing = false;
            this.was_served = false;
            this.is_rejected_by_kitchen = false;
        }

        @Override
        public String toString() {
            return meal_name;
        }
    }
}
