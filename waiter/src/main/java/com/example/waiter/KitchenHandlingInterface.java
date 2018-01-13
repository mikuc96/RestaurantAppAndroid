package com.example.waiter;


public interface KitchenHandlingInterface {
    int rejectOrder(Integer[] order);
    int updateOrderTimer(Integer[] order);
    int finishOrder(Integer[] order);
    int startOrderPreparing(Integer[] order);
}
