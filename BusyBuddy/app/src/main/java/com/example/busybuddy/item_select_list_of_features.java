package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class item_select_list_of_features extends AppCompatActivity {
    ImageButton imageButton_list_of_features;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_features);

        imageButton_list_of_features = findViewById(R.id.imageButton_list_of_features);

        imageButton_list_of_features.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_list_of_features.this, FaqMain.class);
                startActivity(intent);
            }
        });
    }
}