package com.example.kamil.restaurant.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.kamil.restaurant.DataBase.DishesDataBase;
import com.example.kamil.restaurant.DetailActivity;
import com.example.kamil.restaurant.DetailDish;
import com.example.kamil.restaurant.R;

import java.util.zip.Inflater;

/**
 * Created by mikuc on 1/10/18.
 */

public class RatingDialog extends AppCompatDialogFragment {
    android.support.v7.app.AlertDialog.Builder alert;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder rating = new AlertDialog.Builder(getActivity());
        LayoutInflater inf=getActivity().getLayoutInflater();
        View view=inf.inflate(R.layout.rate_dialog,null);
        rating.setView(view);
        rating.setTitle("Rate");
        rating.setPositiveButton("Oceń mnie :) słońce", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert= new android.support.v7.app.AlertDialog.Builder(getContext());

                Helper.showDialog(alert,getActivity(),"Oceniono");

            }
        });

        return  rating.create();
    }


}
