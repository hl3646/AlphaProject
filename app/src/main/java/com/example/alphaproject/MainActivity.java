package com.example.alphaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button registerButton;
    EditText emailEditText;
    EditText passwordEditText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = findViewById(R.id.button);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add("Text Storage");
        menu.add("Image Storage");
        menu.add("NFC Scan");
        menu.add("QR Code Scan");

        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Text Storage"))
        {
            // send to Text storage activity
            startActivity(new Intent(MainActivity.this, TextStorage.class));
        }
        else if (st.equals("Image Storage"))
        {
            // send to Image storage activity
            startActivity(new Intent(MainActivity.this, ImageStorage.class));
        }
        else if (st.equals("NFC Scan"))
        {
            // send to NFC scan activity
            startActivity(new Intent(MainActivity.this, NFCScan.class));
        }
        else if (st.equals("QR Code Scan"))
        {
            // send to QR code scan activity

        }
        return super.onOptionsItemSelected(item);
    }

    public void Register(View view)
    {
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful, navigate to ImageStorage
                            startActivity(new Intent(MainActivity.this, ImageStorage.class));
                        }
                        else
                        {
                            // Registration failed
                            Toast.makeText(MainActivity.this, "Registration failed :(", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}