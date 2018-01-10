package com.example.kamil.restaurant.Dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;


public class Helper {

    private AlertDialog.Builder builder1;
    public static void showDialog(AlertDialog.Builder builder1, Context cnx, String msg) {

        builder1.setTitle("Info");
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();

    }

}
