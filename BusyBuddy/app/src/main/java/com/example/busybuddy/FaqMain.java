package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FaqMain extends AppCompatActivity {
    String[] item = {"About the App", "List of Features"};

    ArrayList list= new ArrayList(Arrays.asList(item));
    String[] item1 = {"Notifications"};

    ArrayList list1= new ArrayList(Arrays.asList(item1));
    String[] item2 = {"Tasks", "How to Create List?", "How to Delete List?"};

    ArrayList list2= new ArrayList(Arrays.asList(item2));
    String[] item3 = {"Contact Us"};

    ArrayList list3= new ArrayList(Arrays.asList(item3));

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    ArrayAdapter<String> adapterItems2;
    ArrayAdapter<String> adapterItems3;

    private boolean spinnerItemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_main);

        ImageButton app= findViewById(R.id.app);
        ImageButton feature= findViewById(R.id.features);
        ImageButton notifications= findViewById(R.id.notifications);
        ImageButton createtask= findViewById(R.id.task);
        ImageButton deletetask= findViewById(R.id.delete);
        ImageButton contactus= findViewById(R.id.contactUs);

        app.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_about_the_app.class);
                startActivity(intent);
            }
        });

        feature.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_list_of_features.class);
                startActivity(intent);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_notifications.class);
                startActivity(intent);
            }
        });

        createtask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_task_create.class);
                startActivity(intent);
            }
        });

        deletetask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_task_delete.class);
                startActivity(intent);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(FaqMain.this, item_select_contact_us.class);
                startActivity(intent);
            }
        });


    }

}