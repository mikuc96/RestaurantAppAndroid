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

public class LoginDialog extends AppCompatDialogFragment {

    TextView email, password;
    AlertDialog.Builder builder1;

    public Boolean checkNotNull(TextView email, TextView password)
    {
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty() )
        {
            return Boolean.FALSE;
        }else return Boolean.TRUE;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
        LayoutInflater li=getActivity().getLayoutInflater();
        View view=li.inflate(R.layout.activity_login, null);
        builder1=new AlertDialog.Builder(getContext());
        email = (TextView) view.findViewById(R.id.login_email);
        password = (TextView) view.findViewById(R.id.login_password);
        ad
        .setView(view)
        .setTitle("Zaloguj")
        .setPositiveButton("Zaloguj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            if(StartActivity.userSnap.child(email.getText().toString()).exists() && checkNotNull(email,password) )
            {
                if(StartActivity.userSnap.child(email.getText().toString()).child("password").getValue()
                        .equals(password.getText().toString()))
                {
                    Helper.showDialog(builder1,getContext(),"Zalogowanie przebiegło pomyślnie!");
                    StartActivity.who=StartActivity.userSnap.child(email.getText().toString()).child("name").getValue().toString();
                    StartActivity.btnProfile.setVisibility(View.VISIBLE);
                    StartActivity.logged=Boolean.TRUE;

                }else Helper.showDialog(builder1,getContext(),"Nieprawidłowe dane");

            }else Helper.showDialog(builder1,getContext(),"Nieprawidłowe dane");

            }
        })
        .setNegativeButton("Wyjdź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return  ad.create();

    }

}
