package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display the custom dialog for changing the password
                showChangePasswordDialog();
            }
        });
    }

    // Method to display the custom dialog for changing the password
    private void showChangePasswordDialog() {
        // Create a custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_password);

        // Initialize UI elements from the dialog layout
        final EditText newPasswordEditText = dialog.findViewById(R.id.newPasswordEditText);
        final EditText confirmPasswordEditText = dialog.findViewById(R.id.confirmPasswordEditText);
        Button changePasswordButton = dialog.findViewById(R.id.confirmButton);

        // Set a click listener for the "Change Password" button in the dialog
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the new password and confirm password from the EditText fields
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Check if the passwords match and perform the password change logic
                if (newPassword.equals(confirmPassword)) {
                    // Implement your password change logic here

                    // Dismiss the dialog after password change is successful
                    dialog.dismiss();
                } else {
                    // Show an error message or handle password mismatch
                    // For example, you can display a Toast message
                    // Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Show the dialog
        dialog.show();
    }
}
