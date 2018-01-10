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
import com.example.kamil.restaurant.Helper;
import com.example.kamil.restaurant.R;
import com.example.kamil.restaurant.StartActivity;

public class LoginDialog extends AppCompatDialogFragment {

    TextView email, password;
    AlertDialog.Builder builder1;
    Helper help;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int temp=View.VISIBLE;
        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
        LayoutInflater li=getActivity().getLayoutInflater();
        View view=li.inflate(R.layout.activity_login, null);
        help=new Helper();
        builder1=new AlertDialog.Builder(getContext());
        email = (TextView) view.findViewById(R.id.login_email);
        password = (TextView) view.findViewById(R.id.login_password);
        ad
        .setView(view)
        .setTitle("Login")
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (UserDataBase.loginUser(email.getText().toString(), password.getText().toString())) {

                    StartActivity.btnProfile.setVisibility(temp);
                    help.showDialog(builder1, getContext(), "Login, ok!");
                    StartActivity.logged = Boolean.TRUE;
                    StartActivity.who = email.getText().toString();

                } else {
                    help.showDialog(builder1, getContext(), "Login filed!");
                }
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return  ad.create();

    }

}
