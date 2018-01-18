package com.example.kamil.restaurant.Dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.kamil.restaurant.DataBase.UserDataBase;
import com.example.kamil.restaurant.R;
import com.example.kamil.restaurant.StartActivity;

/**
 * Created by mikuc on 1/10/18.
 */

public class RegisterDialog extends AppCompatDialogFragment {

    UserDataBase us;
    TextView name, email, password;
    AlertDialog.Builder builder1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
        builder1=new AlertDialog.Builder(getContext());
        LayoutInflater li=getActivity().getLayoutInflater();
        View view=li.inflate(R.layout.activity_register, null);
        ad.setView(view);
        ad.setTitle("Register");
        ad.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(StartActivity.userSnap.child(email.getText().toString()).exists())
                {
                    Helper.showDialog(builder1,getContext(),"Your exist in database, please login!");
                }else{
                    us=new UserDataBase(name.getText().toString(),email.getText().toString(),password.getText().toString());
                    StartActivity.userRef.child(email.getText().toString()).setValue(us);
                    Helper.showDialog(builder1,getContext(),"Your registration was succesful!");
                }
            }
        });
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });


        name=(TextView)view.findViewById(R.id.input_name);
        email=(TextView)view.findViewById(R.id.input_email);
        password=(TextView)view.findViewById(R.id.input_password);
        builder1=new android.support.v7.app.AlertDialog.Builder(getContext());
        builder1=new android.support.v7.app.AlertDialog.Builder(getContext());
        return ad.create();
    }
}
