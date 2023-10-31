package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class item_select_task_create extends AppCompatActivity {
    ImageButton imageButton_task_create;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_create_list);

        imageButton_task_create = findViewById(R.id.imageButton_task_create);

        imageButton_task_create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_task_create.this, FaqMain.class);
                startActivity(intent);
            }
        });
    }
}