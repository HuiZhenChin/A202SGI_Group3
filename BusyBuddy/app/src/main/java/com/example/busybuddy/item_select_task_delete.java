package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class item_select_task_delete extends AppCompatActivity {
    ImageButton imageButton_task_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_delete_list);

        imageButton_task_delete = findViewById(R.id.imageButton_task_delete);

        imageButton_task_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_task_delete.this, FaqMain.class);
                startActivity(intent);
            }
        });
    }
}