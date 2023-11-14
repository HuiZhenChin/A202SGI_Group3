package com.example.busybuddy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Reset Password
public class ForgetPass extends AppCompatActivity {

    private DBManager dbManager;
    private TextView user, phrase;
    private int userID;
    private String username, phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass);

        // open the database
        dbManager = new DBManager(this);  // open the database
        dbManager.open();
        user = findViewById(R.id.userText);
        phrase = findViewById(R.id.phraseText);
        Button confirmButton = findViewById(R.id.confirmButton);

        // when the Confirm button is pressed
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = user.getText().toString();  // get the username
                phrases = phrase.getText().toString(); // get the phrase entered
                Boolean validate = dbManager.validateUser(username, phrases);
                // validate the input
                if(validate == true) {
                    // display the dialog for changing the password
                    showChangePasswordDialog();
                }
                // if the input is invalid
                else{
                    Toast.makeText(ForgetPass.this, "User doesn't exist or phrase is incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // method to display the dialog for changing the password
    private void showChangePasswordDialog() {
        // call the dialog layout
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_password);

        // initialize UI elements from the dialog layout
        final EditText newPasswordEditText = dialog.findViewById(R.id.newPasswordEditText);
        final EditText confirmPasswordEditText = dialog.findViewById(R.id.confirmPasswordEditText);
        Button changePasswordButton = dialog.findViewById(R.id.confirmButton);

        // set a click listener for the "Change Password" button in the dialog
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the new password and confirm password from the EditText fields
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // check if the password match and perform the password changing
                if (newPassword.equals(confirmPassword)) {
                    userID = dbManager.getUserID(username);
                    dbManager.resetPass(userID, newPassword);
                    // reset password successful
                    Toast.makeText(ForgetPass.this, "Password changed successfully! \n Remember not to forget password again.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPass.this, LoginActivity.class);
                    startActivity(intent);
                    // dismiss the dialog after password change is successful
                    dialog.dismiss();
                } else {
                    // if password does not match, show the error message
                    Toast.makeText(ForgetPass.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // show the dialog
        dialog.show();
    }
}