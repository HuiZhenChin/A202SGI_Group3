package com.example.project;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.content.Context;
import com.example.project.CustomSpinnerAdapter;


import java.util.Calendar;

public class NewActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    Button dateBtn;
    TextView selectedDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        Spinner savedFolderSpinner = findViewById(R.id.savedFolder);

        // Create an array of CharSequence that includes the hint
        CharSequence[] items = getResources().getTextArray(R.array.savedFolder_dropdown);

        // Create the custom adapter
        CustomSpinnerAdapter  adapter = new CustomSpinnerAdapter (this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        savedFolderSpinner.setAdapter(adapter);

        savedFolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // When an item is selected, you can perform actions here.
                if (position == 0) {

                } else {
                    // A valid item was selected. Perform your desired actions here.
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        Button createtaskbtn = findViewById(R.id.createTask);
        createtaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tasktitleinput = findViewById(R.id.titleInput);
                Button dateinput = findViewById(R.id.dateInput);
                String titlefieldinput = tasktitleinput.getText().toString().trim();
                String datefieldinput = dateinput.getText().toString().trim();
                Spinner savedFolderSpinner = findViewById(R.id.savedFolder);
                CharSequence selectedText = (CharSequence) savedFolderSpinner.getSelectedItem();
                int selectedPosition = savedFolderSpinner.getSelectedItemPosition();

                if (titlefieldinput.isEmpty()) {
                    // Show an error message or indicate that the field is required
                    tasktitleinput.setError("Please enter the task title");

                    return;
                }

               if (datefieldinput.isEmpty()){
                    dateinput.setError("Please choose the date");
                    return;
                }



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
                        //passing context.
                        NewActivity.this,
                        R.style.CustomDatePickerDialogStyle,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //  date to text view.
                                selectedDateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },

                        year, month, day);

                // display date picker dialog.
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

    }





