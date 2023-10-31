package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ImageView showhide = findViewById(R.id.show_hide_password);
        EditText username = findViewById(R.id.usernameInput);
        EditText password = findViewById(R.id.passwordInput);

        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //press to view password
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showhide.setImageResource(R.drawable.show_icon);
                } else {
                  //press to hide password
                  password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                  showhide.setImageResource(R.drawable.hide_icon);
                }


            }
        });


        Button loginButton = findViewById(R.id.loginBtn);


        // Set click listeners for the plus and minus buttons
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String usernamefieldinput = username.getText().toString().trim();

                String passwordfieldinput = password.getText().toString().trim();

                if (usernamefieldinput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();

                }

                if (passwordfieldinput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();

                }

                if (!usernamefieldinput.isEmpty() && !passwordfieldinput.isEmpty())

                    navigateToNextActivity();
            }
        });

    }

    public void openNewActivity(){
        Intent intent = new Intent(this, PriorityList.class);
        startActivity(intent);
    }

    private void navigateToNextActivity() {
        Intent intent = new Intent(this, MainActivity.class); // Replace with the actual activity you want to navigate to
        startActivity(intent);
    }

    public void openSignUp(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);
    }

}




