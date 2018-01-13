package com.example.waiter;


public class KitchenHandling implements KitchenHandlingInterface {
    @Override
    public int rejectOrder(Integer[] order) {
        return 0;
    }

    @Override
    public int updateOrderTimer(Integer[] order) {
        return 0;
    }

    @Override
    public int finishOrder(Integer[] order) {
        return 0;
    }

    @Override
    public int startOrderPreparing(Integer[] order) {
        return 0;
    }
}
