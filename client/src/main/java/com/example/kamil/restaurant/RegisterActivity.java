package com.example.kamil.restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //z grubsza tak powinno dzialac zapisywanie do bazy
    /*rejestruj() {

        if(wszystkie sprawdzenia dlugosci i poprawnosci ok)
        {

            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new com.example.kamil.restaurant.Models.Osoba(id, login, znizka, email, haslo)
                    );
        }


        However, if you observe the code above, you'll see that we're calling setValue() without specifying any key.
        That's allowed only because the call to the setValue() method is preceded by a call to the push() method, which automatically generates a new key.

        czyli push() generuje nowy klucz, a setvalue modyfikuje

    }
    */
}



