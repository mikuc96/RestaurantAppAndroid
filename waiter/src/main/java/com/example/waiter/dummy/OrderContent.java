package com.example.waiter.dummy;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample meal_name for user interfaces created by
 * TODO: Replace all uses of this class before publishing your app.
 */
public class OrderContent {

    public static List<SingleOrder> currentOrderList = new ArrayList<SingleOrder>();
    public static List<SingleOrder> processingOrderList = new ArrayList<SingleOrder>();
    public static Map<String, SingleOrder> currentOrderMap = new HashMap<String, SingleOrder>();
    public static Map<String, SingleOrder> processingOrderMap = new HashMap<String, SingleOrder>();
    static Random tmp_generator = new Random();
    private static final int COUNT = 1;

    static { // todo usunac
        for (int i = 1; i <= COUNT; i++) {
            addSingleOrderToOrderList(i , i+50, i %9);
//            moveElementToProcessingList(createSingleOrderData(i+10, i+30, i%9));
            SingleOrder item = createSingleOrderData(10 , 99, 23);
        }
    }

    public static void addSingleOrderToOrderList(int order_id, int meal_id, int table_id) {
        SingleOrder item = createSingleOrderData(order_id , meal_id, table_id);
        currentOrderList.add(item);
        currentOrderMap.put(String.valueOf(item.order_id), item);
    }

    private static void removeElementFromList(SingleOrder item){
        currentOrderList.remove(item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentOrderMap.remove(String.valueOf(item.order_id), item);
        }
    }

    public static void removeElementFromOrderList(int order_id, int meal_id, int table_id) {
        SingleOrder item = createSingleOrderData(order_id , meal_id, table_id);
        removeElementFromList(item);
    }


    public static void moveElementToProcessingList(int position) {
        Log.d("ppppppposition ", String.valueOf(position));
        SingleOrder item = currentOrderList.get(position);
//        removeElementFromList(item);
        processingOrderList.add(item);
        processingOrderMap.put(String.valueOf(item.order_id), item);
    }

    private static SingleOrder createSingleOrderData(int unique_order_id, int meal_id, int table_id) {
//        return new SingleOrder(String.valueOf(unique_order_id), "Item " + unique_order_id, getRecipe(unique_order_id), table_id);
        return new SingleOrder(unique_order_id, meal_id, getRecipe(unique_order_id), table_id);
    }

    private static String getRecipe(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Recipe of Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore recipe information here.");
        }
        return builder.toString();
    }

    public static class SingleOrder {
        public final Integer order_id;
        public String meal_name = "Jeszcze Glupsza Nazwa Potrawy"; //final
        public final String recipe;
        public final Integer table_id;
        public Integer timer;
        public Boolean is_paying;
        public Boolean is_accepted_by_waiter;
        public Boolean is_preparing;
        public Boolean is_prepared;
        public Boolean is_just_served;
        public Boolean is_rejected_by_kitchen;


        public SingleOrder(int order_id, int meal_id, String recipe, int table_id) {
            this.order_id = order_id;
            if(meal_id > 0 ) // todo pobrac z bazy
                this.meal_name = String.valueOf(tmp_generator.nextInt());
            this.recipe = recipe;
            this.table_id = table_id;
            this.timer = 120;
            this.is_paying = false;
            this.is_accepted_by_waiter = false;
            this.is_preparing = false;
            this.is_prepared = false;
            this.is_just_served = false;
            this.is_rejected_by_kitchen = false;
        }

        @Override
        public String toString() {
            return meal_name;
        }
    }
}
