package com.example.waiter.dummy;

import android.os.Build;
import android.util.Log;

import java.lang.ref.SoftReference;
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
            addSingleOrderToOrderList(i, i+2, i+50, i %9);
//            moveElementToProcessingList(createSingleOrderData(i+10, i+30, i%9));
            SingleOrder item = createSingleOrderData(10, 12 , 99, 23);
        }
    }

    public static void addSingleOrderToOrderList(int client_id, int order_id, int meal_id, int table_id) {
        SingleOrder item = createSingleOrderData(client_id, order_id , meal_id, table_id);
        currentOrderList.add(item);
        currentOrderMap.put(String.valueOf(item.order_id), item);
    }

    private static void removeElementFromList(SingleOrder item){
        currentOrderList.remove(item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentOrderMap.remove(String.valueOf(item.order_id), item);
        }
    }

    public static void removeElementFromOrderList(int client_id, int order_id, int meal_id, int table_id) {
        SingleOrder item = createSingleOrderData(client_id, order_id , meal_id, table_id);
        removeElementFromList(item);
    }


    public static void moveElementToProcessingList(int position) {
        Log.d("ppppppposition ", String.valueOf(position));
        SingleOrder item = currentOrderList.get(position);
        processingOrderList.add(item);
        processingOrderMap.put(String.valueOf(item.order_id), item);
    }

    private static SingleOrder createSingleOrderData(int client_id, int order_id, int meal_id, int table_id) {
//        return new SingleOrder(String.valueOf(order_id), "Item " + order_id, getRecipe(order_id), table_id);
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
        public Boolean is_preparing;
        public Boolean is_prepared;
        public Boolean is_just_served;
        public Boolean is_rejected_by_kitchen;


        public SingleOrder(int client_id, int order_id, int meal_id, String recipe, int table_id) {
            this.order_id = String.valueOf(order_id);
            this.table_id = String.valueOf(table_id);
            this.meal_id = String.valueOf(meal_id);
            this.client_id = String.valueOf(client_id);
            if(meal_id > 0 ) // todo pobrac z bazy
                this.meal_name = String.valueOf(tmp_generator.nextInt());
            this.recipe = recipe;
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
