package com.example.waiter;


public interface KitchenHandlingInterface {
    void rejectOrder(Integer order);
    void updateOrderTimer(Integer order, int timeUnit);
    void finishOrder(Integer order);
    void startOrderPreparing(Integer order);
}
