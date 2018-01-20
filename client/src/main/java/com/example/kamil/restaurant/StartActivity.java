package com.example.kamil.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kamil.restaurant.DataBase.DishesDataBase;
import com.example.kamil.restaurant.Dialog.Helper;
import com.example.kamil.restaurant.Dialog.LoginDialog;
import com.example.kamil.restaurant.Dialog.RegisterDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {

    public static Boolean logged=Boolean.FALSE;
    public static String who;
    AlertDialog.Builder builder;
    public static Button btnProfile;
    FirebaseDatabase database;
    public static DatabaseReference userRef;
    public static DataSnapshot userSnap;
    public static DatabaseReference menuRef;
    public static DataSnapshot menuSnap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnProfile=(Button)findViewById(R.id.button4);
        btnProfile.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance();
        DishesDataBase.sentToFirebase();
        userRef = database.getReference("Users");
        menuRef = database.getReference("Menu");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userSnap=dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        menuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuSnap =dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void menu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logowanie(View view) {
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
