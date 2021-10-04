package com.example.retrocat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ClickedActivity extends AppCompatActivity {
    ImagesResponse imagesResponse;

    ImageView imageView;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);

        imageView = findViewById(R.id.selectedImage);


        Intent intent = getIntent();

        if(intent.getExtras() != null){
            imagesResponse = (ImagesResponse) intent.getSerializableExtra("data");
            Glide.with(this).load(imagesResponse.getUrl()).into(imageView);


        }
    }
}