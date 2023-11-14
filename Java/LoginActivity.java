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

// Login Page
public class LoginActivity extends AppCompatActivity {
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ImageView showhide = findViewById(R.id.show_hide_password);
        EditText username = findViewById(R.id.usernameInput);
        EditText password = findViewById(R.id.passwordInput);

        // open the database
        dbManager = new DBManager(this);
        dbManager.open();

        // password eye icon (show and hide password)
        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // press to view password
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showhide.setImageResource(R.drawable.show_icon);
                } else {
                    // press to hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showhide.setImageResource(R.drawable.hide_icon);
                }

            }
        });


        Button loginButton = findViewById(R.id.loginBtn);
        // press to login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the username and password input
                String usernamefieldinput = username.getText().toString().trim();
                String passwordfieldinput = password.getText().toString().trim();

                // errror handling
                if (usernamefieldinput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();

                }

                if (passwordfieldinput.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();

                }

                // input validation
                if (!usernamefieldinput.isEmpty() && !passwordfieldinput.isEmpty()) {
                    // check for valid username and password
                    Boolean loginCred = dbManager.login(usernamefieldinput, passwordfieldinput);
                    if(loginCred == true){
                        // if username and password match
                        Toast.makeText(getApplicationContext(), "Welcome Back!", Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);

                        // pass extra values to the intent
                        intentLogin.putExtra("usernameValue", usernamefieldinput);
                        intentLogin.putExtra("passwordValue", passwordfieldinput);
                        startActivity(intentLogin);

                        // if login credential is wrong
                    } else if (loginCred == false) {
                        // show error message
                        Toast.makeText(getApplicationContext(), "Username or password doesn't match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // if user press on the Sign Up textview
    public void openSignUp(View view) {
        // direct to the Sign Up Page
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    // if user press on the Forget Password textview
    public void forget(View view) {
        // direct to the ForgetPass Page
        Intent intent = new Intent(this, ForgetPass.class);
        startActivity(intent);
    }

}







