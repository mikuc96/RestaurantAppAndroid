package com.example.waiter;

/**
 * Communication waiter - client
 */

interface OrderCommunication {
    int takeOrder(String mealId);
    int acceptOrderCancellation(String mealId);
    int notifyOrderProgress(String mealId);
    int showPaymentNotification(String mealId);
}
