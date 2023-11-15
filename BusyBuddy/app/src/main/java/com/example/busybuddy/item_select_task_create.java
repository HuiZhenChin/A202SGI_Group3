package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

// FAQ: Create New Task
public class item_select_task_create extends AppCompatActivity {
    ImageButton imageButton_task_create;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_create_list);

        imageButton_task_create = findViewById(R.id.imageButton_task_create);

        // get username
        Intent intentLogin = getIntent();
        String usernameValue = intentLogin.getStringExtra("usernameValue");

        // return to the FAQ Main Page
        imageButton_task_create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_task_create.this, FaqMain.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });
    }
}
