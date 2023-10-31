package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class MyAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
    }
    public void edit_btn(View view){

        Intent intent = new Intent(this, EditMyAccount.class);
        startActivity(intent);
    }
}