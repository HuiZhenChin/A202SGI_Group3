package com.example.faq;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    String[] item = {"About the App", "List of Features"};
    String[] item1 = {"Notifications"};
    String[] item2 = {"Tasks", "How to Create List?", "How to Delete List?"};
    String[] item3 = {"Contact Us"};

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView1;
    AutoCompleteTextView autoCompleteTextView2;
    AutoCompleteTextView autoCompleteTextView3;

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    ArrayAdapter<String> adapterItems2;
    ArrayAdapter<String> adapterItems3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("About the App"))
                {
                    Intent intent = new Intent(MainActivity.this, item_select_about_the_app.class);
                    startActivity(intent);
                }
                else if(item.equals("List of Features"))
                {
                    Intent intent1 = new Intent(MainActivity.this, item_select_list_of_features.class);
                    startActivity(intent1);
                }
            }
        });

        autoCompleteTextView1 = findViewById(R.id.auto_complete_txt1);
        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_item, item1);

        autoCompleteTextView1.setAdapter(adapterItems1);

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item1 = adapterView.getItemAtPosition(i).toString();
                if(item1.equals("Notifications"))
                {
                    Intent intent2 = new Intent(MainActivity.this, item_select_notifications.class);
                    startActivity(intent2);
                }
            }
        });

        autoCompleteTextView2 = findViewById(R.id.auto_complete_txt2);
        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_item, item2);

        autoCompleteTextView2.setAdapter(adapterItems2);

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item2 = adapterView.getItemAtPosition(i).toString();
                if(item2.equals("Tasks"))
                {
                    Intent intent3 = new Intent(MainActivity.this, item_select_task_description.class);
                    startActivity(intent3);
                }
                else if(item2.equals("How to Create List?"))
                {
                    Intent intent4 = new Intent(MainActivity.this, item_select_task_create.class);
                    startActivity(intent4);
                }
                else if(item2.equals("How to Delete List?"))
                {
                    Intent intent5 = new Intent(MainActivity.this, item_select_task_delete.class);
                    startActivity(intent5);
                }
            }
        });

        autoCompleteTextView3 = findViewById(R.id.auto_complete_txt3);
        adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_item, item3);

        autoCompleteTextView3.setAdapter(adapterItems3);

        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item3 = adapterView.getItemAtPosition(i).toString();
                if(item3.equals("Contact Us"))
                {
                    Intent intent6 = new Intent(MainActivity.this, item_select_contact_us.class);
                    startActivity(intent6);
                }
            }
        });
    }
}