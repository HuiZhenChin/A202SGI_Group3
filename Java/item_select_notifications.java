package com.example.faq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
public class item_select_notifications extends AppCompatActivity {
    ImageButton imageButton_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        imageButton_notifications = findViewById(R.id.imageButton_notifications);

        imageButton_notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_notifications.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}