package com.example.waiter;

/**
 * Communication waiter - client
 */

interface OrderCommunicationWithClient {
    int takeOrder(String mealId) throws InterruptedException;
    int acceptOrderCancellation(String mealId);
    int notifyOrderProgress(String mealId) throws InterruptedException;
    int showPaymentNotification(String mealId);
}
