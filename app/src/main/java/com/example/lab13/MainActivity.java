package com.example.lab13;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_REQUEST = 101;
    private Uri videoUri = null;
    // Initialize variables
    ImageView imageView;
    Button btOpen, btVideo, btVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Assign variables
        imageView = findViewById(R.id.image_view);
        btOpen = findViewById(R.id.bt_open);
        btVideo = findViewById(R.id.bt_intent);

        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callActivity();

            }


            private void callActivity() {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });


// Request for camera permission
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new
                            String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void captureVideo(View view) {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (videoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(videoIntent, VIDEO_REQUEST);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
            videoUri = data.getData();}

            if (requestCode == 100) {
// Get capture image

                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
// Set capture image to ImageView
                imageView.setImageBitmap(captureImage);
            }
            super.onActivityResult(requestCode, resultCode, data);

    }
}