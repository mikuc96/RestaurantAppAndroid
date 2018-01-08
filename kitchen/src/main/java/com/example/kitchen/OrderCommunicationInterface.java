package com.example.kitchen;

/**
 * Communication kitchen - waiter
 */

public interface OrderCommunicationInterface {
    int startMealPreparing(String mealId);
    int cancelPreparing(String mealId);
    int notifyOrderProgress(String mealId);

}
