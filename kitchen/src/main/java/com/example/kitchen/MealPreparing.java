package com.example.kitchen;

/**
 * Communication kitchen - waiter
 */

public interface MealPreparing {
    int startMealPreparing(String mealId);
    int cancelPreparing(String mealId);
    int notifyOrderProgress(String mealId);

}
