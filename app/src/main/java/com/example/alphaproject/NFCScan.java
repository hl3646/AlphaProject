package com.example.alphaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NFCScan extends AppCompatActivity
{
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    TextView nfcTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcscan);

        nfcTextView = findViewById(R.id.textView5);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null)
        {
            Toast.makeText(this, "NFC not supported on this device", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Text Storage");
        menu.add("Image Storage");
        menu.add("QR Code Scan");
        menu.add("Register");

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Text Storage"))
        {
            // send to Text storage activity
            startActivity(new Intent(NFCScan.this, TextStorage.class));
        }
        else if (st.equals("Image Storage"))
        {
            // send to NFC scan activity
            startActivity(new Intent(NFCScan.this, ImageStorage.class));
        }
        else if (st.equals("QR Code Scan"))
        {
            // send to QR code scan activity

        }
        else if (st.equals("Register"))
        {
            // send to register activity
            startActivity(new Intent(NFCScan.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void readFromTag(View view)
    {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null)
        {
            Toast.makeText(this, "this device does not support NFC", Toast.LENGTH_LONG).show();

        }

        readfromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //IntentFilter tagDetected =
    }
}