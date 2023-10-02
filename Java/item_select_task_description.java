package com.example.faq;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
public class item_select_task_description extends AppCompatActivity {
    ImageButton imageButton_task_description;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_subpage);

        imageButton_task_description = findViewById(R.id.imageButton_task_description);

        imageButton_task_description.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_task_description.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}