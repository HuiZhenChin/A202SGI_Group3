package com.example.project3;


import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.content.Context;
import android.widget.EditText;

import com.example.project3.R;
import com.google.android.material.snackbar.Snackbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String name="";
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    String items[] = {"Assignment", "Work", "Personal", "Create New Folder"};

    ArrayList list= new ArrayList(Arrays.asList(items));
    Button dateBtn;
    TextView selectedDateView;


    private EditText taskTitleEditText;
    private TextView dateInputTextView;


    public boolean isEnabled(int position) {
        if (position == 0) {

            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        //CustomSpinnerAdapter  adapter2 = new CustomSpinnerAdapter (this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        Spinner savedFolderSpinner = findViewById(R.id.savedFolder);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savedFolderSpinner.setAdapter(adapter);

        RelativeLayout createTaskLayout = findViewById(R.id.createtasklayout);
        RelativeLayout folderLayout = findViewById(R.id.newfolderlayout);

        taskTitleEditText = findViewById(R.id.titleInput);
        dateInputTextView = findViewById(R.id.selectedDate);

        savedFolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //when an item is selected
                if (position ==0) {

                    //don't save to database

                } else if (position == list.size() - 1) {   //if create new folder is selected

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.create_new_folder, null);

                    //constant
                    final EditText editTextFolderName = dialogView.findViewById(R.id.newFolderName);

                    final AlertDialog dialog = builder.setView(dialogView).create();
                    Button create = dialogView.findViewById(R.id.createNewFolderBtn);

                    create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //while create is pressed
                            String newfoldername = editTextFolderName.getText().toString().trim();
                            if (!newfoldername.isEmpty()) {
                                //insert as the element above the create new folder option
                                int insertIndex = list.size() - 1;

                                list.add(insertIndex, newfoldername);
                                adapter.notifyDataSetChanged();
                                savedFolderSpinner.setAdapter(adapter);  //add item to the spinner

                                Toast.makeText(getApplicationContext(), "New Folder "+ newfoldername + " inserted", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        }
                    });


                    Button cancel = dialogView.findViewById(R.id.cancelBtn);
                    //while cancel is pressed
                    cancel.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();  //activity dismiss
                        }
                    });

                    //AlertDialog dialog = builder.create();

                    dialog.show();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //pass
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



                Snackbar.make(findViewById(android.R.id.content), "New Task Created Successfully!", Snackbar.LENGTH_SHORT).show();

                String taskTitle = taskTitleEditText.getText().toString();
                String selectedDate = dateInputTextView.getText().toString();
                RadioButton high= findViewById(R.id.highPrio);
                RadioButton low= findViewById(R.id.lowPrio);
                RadioButton medium= findViewById(R.id.mediumPrio);
                String priority;



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




            }
        });





    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, Display_name_activity.class);
        EditText editText = (EditText) findViewById(R.id.titleInput);
        String message = editText.getText().toString();
        intent.putExtra(name, message);
        startActivity(intent);
    }






}
