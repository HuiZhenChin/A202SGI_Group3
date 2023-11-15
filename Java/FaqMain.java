package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

// FAQ (Frequently- asked questions) Main Page to select different category of questions
public class FaqMain extends AppCompatActivity {
    // app information
    String[] item = {"About the App", "List of Features"};
    // notifications
    String[] item1 = {"Notifications"};
    // task information
    String[] item2 = {"Tasks", "How to Create List?", "How to Delete List?"};
    // contact us
    String[] item3 = {"Contact Us"};
    String usernameValue= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_main);

        // access the image button
        ImageButton app= findViewById(R.id.app);
        ImageButton feature= findViewById(R.id.features);
        ImageButton notifications= findViewById(R.id.notifications);
        ImageButton createtask= findViewById(R.id.task);
        ImageButton deletetask= findViewById(R.id.delete);
        ImageButton contactus= findViewById(R.id.contactUs);

        // get username
        Intent intentLogin = getIntent();
        usernameValue = intentLogin.getStringExtra("usernameValue");

        // FAQ Question: About the App
        app.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_about_the_app.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });

        // FAQ Question: List of Features
        feature.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_list_of_features.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });

        // FAQ Question: Notifications
        notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_notifications.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });

        // FAQ Question: Create New Task
        createtask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_task_create.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });

        // FAQ Question: Delete Task
        deletetask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_task_delete.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });

        // FAQ Question: Contact Us
        contactus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_contact_us.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });

        // return to main page to access the navigation drawer
        ImageButton menubutton = findViewById(R.id.menuBtn);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain();
            }
        });


    }

    // return to main page
    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usernameValue", usernameValue);
        startActivity(intent);
    }


}