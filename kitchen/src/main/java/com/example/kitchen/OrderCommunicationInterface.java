package com.example.kitchen;

/**
 * Communication kitchen - waiter
 */

public interface OrderCommunicationInterface {
    int startMealPreparing(Integer[] order);
    int cancelPreparing(Integer[] order);
    int notifyOrderProgress(Integer[] order);

}
