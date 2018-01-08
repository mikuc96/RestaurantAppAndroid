package com.example.waiter;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class RecyclerViewsManager {

    FragmentActivity fracAct;

    RecyclerViewsManager(Activity fa){
        fracAct = (FragmentActivity)fa;
    }

    public void refreshRecyclerLists(){
        refreshWaitingForAcceptionList();
        refreshInPreparingList();
    }


    private void refreshWaitingForAcceptionList(){
        OrdersWaitingForAcceptionFragment f1 = new OrdersWaitingForAcceptionFragment();
        replaceFragments(R.id.elements_waiting_for_acceptation, f1);
    }


    private void refreshInPreparingList(){
        OrdersInPreparingFragment f1 = new OrdersInPreparingFragment();
        replaceFragments(R.id.elements_preparing_in_kitchen, f1);
    }


    private void replaceFragments(int previousFragment, Fragment newFragment){
        FragmentTransaction tr1 = fracAct.getSupportFragmentManager().beginTransaction();
        tr1.replace(previousFragment, newFragment);
        tr1.addToBackStack(null);
        tr1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tr1.commit();
    }
}
