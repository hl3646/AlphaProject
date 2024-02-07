package com.example.alphaproject;

import static com.example.alphaproject.FBRef.refMessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

public class TextStorage extends AppCompatActivity {

    Message dbText;

    String str, updatedValue;

    String textKey, text;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_storage);

        // init editText and TextView
        editText = findViewById(R.id.editTextTextMultiLine);
        textView = findViewById(R.id.textView3);

        // init dbtext object to hold the key and value on start
        // create listener for database datachange to save the updated value on change
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updatedValue = "";
                for (DataSnapshot data : snapshot.getChildren())
                {
                    updatedValue = data.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        // connect listener to the db reference
        refMessage.addValueEventListener(messageListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Image Storage");
        menu.add("NFC Scan");
        menu.add("QR Code Scan");
        menu.add("Register");

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Image Storage"))
        {
            // send to Image storage activity
            startActivity(new Intent(TextStorage.this, ImageStorage.class));

        }
        else if (st.equals("NFC Scan"))
        {
            // send to NFC scan activity
            startActivity(new Intent(TextStorage.this, NFCScan.class));
        }
        else if (st.equals("QR Code Scan"))
        {
            // send to QR code scan activity

        }
        else if (st.equals("Register"))
        {
            // send to register activity
            startActivity(new Intent(TextStorage.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(View view)
    {
        // get the new value to save from the edittext
        str = editText.getText().toString();
        // save the value in the database
        refMessage.child("your text").setValue(str);

    }



    public void load(View view)
    {
        // after the change the listener is activated and saves the new value in updatedvalue string
        // so when we want to load the text we can just put the updatedValue sting in the textview
        //Toast.makeText(TextStorage.this, updatedValue, Toast.LENGTH_LONG).show();
        textView.setText(updatedValue);
    }
}