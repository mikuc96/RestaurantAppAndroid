package com.example.kamil.restaurant;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kamil.restaurant.Dialog.Helper;
import com.example.kamil.restaurant.Dialog.LoginDialog;
import com.example.kamil.restaurant.Dialog.RatingDialog;
import com.example.kamil.restaurant.Dialog.RegisterDialog;

public class StartActivity extends AppCompatActivity {

    public static Boolean logged=Boolean.FALSE;
    public static String who;
    AlertDialog.Builder builder;
    public static Button btnProfile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnProfile=(Button)findViewById(R.id.button4);
        btnProfile.setVisibility(View.GONE);
    }

    public void menu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logowanie(View view) {
//        RatingDialog rd=new RatingDialog();
//        rd.show(getSupportFragmentManager(),"ds");
        LoginDialog lg=new LoginDialog();
        lg.show(getSupportFragmentManager(),"Login");
    }

    public void rejestracja(View view) {
        RegisterDialog rd=new RegisterDialog();
        rd.show(getSupportFragmentManager(),"Register");
    }

    public void startProfile(View view) {
        if(logged)
        {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }else {
            builder=new AlertDialog.Builder(this);
            Helper.showDialog(builder,getApplicationContext(),"You're not logged in!");
        }
    }
}
