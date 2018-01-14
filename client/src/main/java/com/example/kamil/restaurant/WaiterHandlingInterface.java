package com.example.kamil.restaurant;


public interface WaiterHandlingInterface {
    void notifyOrderPrepared(Integer order);
    void notifyOrderTime(Integer order);
    void notifyOrderRejected(Integer order);
}
