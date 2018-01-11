package com.example.kamil.restaurant;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=(TextView)findViewById(R.id.name_profile);
        name.setText(StartActivity.who);
    }

}
