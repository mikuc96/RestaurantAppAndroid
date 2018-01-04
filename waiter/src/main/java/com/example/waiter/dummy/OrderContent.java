package com.example.waiter.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample meal_name for user interfaces created by
 * TODO: Replace all uses of this class before publishing your app.
 */
public class OrderContent {

    public static final List<SingleOrder> currentOrderList = new ArrayList<SingleOrder>();
    public static final Map<String, SingleOrder> ITEM_MAP = new HashMap<String, SingleOrder>();

    private static final int COUNT = 25;

    static { // todo usunac
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addSingleOrderToOrderList(createSingleOrderData(i, i+100, i%9));
        }
    }

    public static void addSingleOrderToOrderList(SingleOrder item) {
        currentOrderList.add(item);
        ITEM_MAP.put(String.valueOf(item.order_id), item);
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
                this.meal_name = "Glupia potrawa";
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
