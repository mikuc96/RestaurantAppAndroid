package com.example.waiter;

/**
 * Communication waiter - client
 */

interface ClientHandlingInterface {
    int takeOrder(Integer[] order) throws InterruptedException;
    int acceptOrderCancellation(Integer[] order);
    int notifyOrderProgress(Integer[] order) throws InterruptedException;
    int showPaymentNotification(Integer[] order);
}
