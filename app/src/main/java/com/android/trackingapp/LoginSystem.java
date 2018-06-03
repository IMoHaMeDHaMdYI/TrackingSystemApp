package com.android.trackingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginSystem extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailField, passwordField;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_system);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        btn = findViewById(R.id.sign_in);

        btn.setOnClickListener((View v) ->{
            String email = emailField.getText().toString().trim();
            String pass = passwordField.getText().toString();
            mAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this, task->{
                       Intent intent = new Intent(this,TrackingActivity.class);
                       startActivity(intent);
                    });

        });

    }
}
