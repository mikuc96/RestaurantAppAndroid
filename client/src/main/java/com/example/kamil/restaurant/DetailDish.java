package com.example.kamil.restaurant;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kamil.restaurant.DataBase.DishesDataBase;
import com.example.kamil.restaurant.Dialog.Helper;
import com.example.kamil.restaurant.Dialog.RatingDialog;


public class DetailDish extends Fragment {
    public static long  dishId=0;
    OrderSent os=new OrderSent();
    Button btnRate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            dishId = savedInstanceState.getLong("dishId");
        } else {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            OrderSent orderSent = new OrderSent();
            ft.replace(R.id.orderSentContainer, orderSent);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        return inflater.inflate(R.layout.fragment_detail_dish, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {

            TextView title=(TextView) view.findViewById(R.id.textTitle);
            TextView price=(TextView) view.findViewById(R.id.textPrice);
            TextView description=(TextView) view.findViewById(R.id.textDescription);
            ImageView image=(ImageView)view.findViewById(R.id.img_dish);

            DishesDataBase dish = DishesDataBase.dishes[(int) dishId];
            title.setText(dish.getName());
            price.setText("Cena: "+dish.getPrice()+"zł");
            description.setText(dish.getDescription());
            description.setText(dish.getDescription());
//            btnRate=(Button)getActivity().findViewById(R.id.ratebtn);
//            showRateDialog(btnRate);
            image.setImageResource(dish.getImg());
            image.setContentDescription(dish.getName());



        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("dishId", dishId);

    }

    public void showRateDialog(Button btn)
    {
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setView(R.layout.rate_dialog).setPositiveButton("Oceń mnie słońce :)",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });

    }

    public void setWorkout(long id) {
        this.dishId = id;
    }
}
