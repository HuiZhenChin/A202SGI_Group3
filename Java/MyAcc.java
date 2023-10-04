package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MyAcc extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myacc);
    }
    public void edit_btn(View view){

        Intent intent = new Intent(this, EditMyAcc.class);
        startActivity(intent);
    }
}