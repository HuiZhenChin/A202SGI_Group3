package com.example.project;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.content.Context;
import com.example.project.CustomSpinnerAdapter;
import com.google.android.material.snackbar.Snackbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    String items[] = {"Choose", "Assignment", "Work", "Personal", "Create New Folder"};
    CustomSpinnerAdapter adapter;

    Spinner savedFolderSpinner;
    Button dateBtn;
    TextView selectedDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        savedFolderSpinner = findViewById(R.id.savedFolder);
        adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savedFolderSpinner.setAdapter(adapter);

        RelativeLayout createTaskLayout = findViewById(R.id.createtasklayout);
        RelativeLayout folderLayout = findViewById(R.id.newfolderlayout);

        savedFolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // When an item is selected, you can perform actions here.
                if (position == 0) {

                } else if (position == items.length - 1) {
                    showCreateFolderDialog();
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });



        Button createtaskbtn = findViewById(R.id.createTask);
        createtaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tasktitleinput = findViewById(R.id.titleInput);
                Button dateinput = findViewById(R.id.dateInput);
                String titlefieldinput = tasktitleinput.getText().toString().trim();
                TextView chosendate = findViewById(R.id.selectedDate);
                String dateText = chosendate.getText().toString().trim();
                String dateview = "Date";
                RadioGroup radio = findViewById(R.id.radioGroup);
                TextView errorText = findViewById(R.id.errortext);
                String errotxt= errorText.getText().toString().trim();
                ImageView errorIcon = findViewById(R.id.errorIcon);

                CharSequence selectedText = (CharSequence) savedFolderSpinner.getSelectedItem();
                int selectedPosition = savedFolderSpinner.getSelectedItemPosition();

                if (titlefieldinput.isEmpty()) {

                    tasktitleinput.setError("Please enter the task title");
                    return;
                }


                if (dateText.contains(dateview)) {

                    //requestfocus() is used to enable the error message to be shown in textview
                    chosendate.requestFocus();
                    chosendate.setError("Please choose the date");
                    return;
                }

                else if (!dateText.contains(dateview)) {
                    chosendate.setError(null);
                }



                //check if any radio button in the RadioGroup is selected
                if (radio.getCheckedRadioButtonId() == -1) {
                    errorText.setError("Please choose the difficulty");
                    //errorIcon.setVisibility(View.VISIBLE);
                    errorText.requestFocus();
                    return;
                }

                else if(radio.getCheckedRadioButtonId() != -1){
                    errorText.setError(null);
                }


                Snackbar.make(findViewById(android.R.id.content), "New Task Created Successfully!", Snackbar.LENGTH_SHORT).show();




            }


        });

        dateBtn = findViewById(R.id.dateInput);
        selectedDateView = findViewById(R.id.selectedDate);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewActivity.this,
                        R.style.CustomDatePickerDialogStyle,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //date chosen by the user
                                String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                //check if the selected date is not earlier than the current date
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, monthOfYear, dayOfMonth);
                                Calendar currentCalendar = Calendar.getInstance();
                                if (selectedCalendar.compareTo(currentCalendar) >= 0) {
                                    selectedDateView.setText(selectedDate);
                                } else {
                                    //handle the invalid selection
                                    Toast.makeText(NewActivity.this, "Please choose a date later than or equal to the current date", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        year, month, day);

                //set the minimum date to the current date, block the earlier date
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

                //display date picker dialog
                datePickerDialog.show();
            }
        });


        ImageButton backbutton = findViewById(R.id.backBtn);


        // Set click listeners for the plus and minus buttons
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });

    }

    public void goPrevious(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void openCreateNewFolderActivity(){
        Intent intent = new Intent(this, CreateNewFolderActivity.class);
        startActivity(intent);
    }

    private void showCreateFolderDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.create_new_folder, null);

            // Find the EditText in the custom dialog layout
            final EditText editTextFolderName = dialogView.findViewById(R.id.newFolderName);

            builder.setView(dialogView)
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                // Handle the "Create" button click
                                String newfoldername = editTextFolderName.getText().toString().trim();
                                if (!newfoldername.isEmpty()) {
                                    // Add the new folder name to your adapter
                                    adapter.add(newfoldername);

                                    // Notify the adapter that the data has changed
                                    adapter.notifyDataSetChanged();

                                    Toast.makeText(getApplicationContext(), "New Folder Inserted", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception here, such as showing an error message.
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here, such as showing an error message.
        }
    }





}



