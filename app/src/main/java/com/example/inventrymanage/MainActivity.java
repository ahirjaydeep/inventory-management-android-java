package com.example.inventrymanage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    EditText getUsername;
    EditText getPassword;
    private final String username = "slayer";
    private final String password = "slayer@1209";
    String usrUsername;
    String usrPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Force Light Mode

        Button btn = findViewById(R.id.btn);
        Intent intent;
        intent = new Intent(this, ListInventorySystemActivity.class);

        getUsername = findViewById(R.id.get_username);
        getPassword = findViewById(R.id.get_password);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usrUsername = getUsername.getText().toString();
                usrPassword = getPassword.getText().toString();

                    btn.setAlpha(1.0f);
                if (username.equals(usrUsername) && password.equals(usrPassword)) {

                    btn.setAlpha(1.0f);
                    startActivity(intent);
                }
                else if (username.equals(usrUsername)) {

                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}