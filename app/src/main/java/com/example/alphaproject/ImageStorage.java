package com.example.alphaproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class ImageStorage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference refImages;

    private ImageView imageView;

    private Uri selectedImageUri;

    private Button pickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_storage);

        pickButton = findViewById(R.id.PickImg);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        refImages = storageReference.child("images");
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Text Storage");
        menu.add("NFC Scan");
        menu.add("QR Code Scan");
        menu.add("Register");

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Text Storage"))
        {
            // send to Text storage activity
            startActivity(new Intent(ImageStorage.this, TextStorage.class));
        }
        else if (st.equals("NFC Scan"))
        {
            // send to NFC scan activity
            startActivity(new Intent(ImageStorage.this, NFCScan.class));
        }
        else if (st.equals("QR Code Scan"))
        {
            // send to QR code scan activity

        }
        else if (st.equals("Register"))
        {
            // send to register activity
            startActivity(new Intent(ImageStorage.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



    public void PickImage(View view) {
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Handle the selected image here
            selectedImageUri = data.getData();

            uploadPicture();
        } else {
            Toast.makeText(ImageStorage.this, "Failed picking image", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadPicture() {
        StorageReference pictureRef = refImages.child("aaa");

        pictureRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ImageStorage.this, "Upload successful", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ImageStorage.this, "Upload failed :(", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void LoadImage(View view)
    {
        // Load and display the image directly in the ImageView
        StorageReference imageRef = refImages.child("aaa");

        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load the image", Toast.LENGTH_LONG).show();
                });
    }

}