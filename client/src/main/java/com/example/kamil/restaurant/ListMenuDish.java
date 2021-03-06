package com.example.kamil.restaurant;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kamil.restaurant.DataBase.DishesDataBase;

public class ListMenuDish extends ListFragment {
    static interface DishListListener {
        void itemClicked(long id);
    };
    
    private DishListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] names = new String[DishesDataBase.dishes.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = DishesDataBase.dishes[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                                                inflater.getContext(),
                                                                android.R.layout.simple_list_item_1,
                                                                names);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.listener = (DishListListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(id);
        }
    }
}
