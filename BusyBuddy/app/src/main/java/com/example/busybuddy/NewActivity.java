package com.example.busybuddy;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

// New Activity: Create New Task
public class NewActivity extends AppCompatActivity {

    private DBManager dbManager;
    private DB db;
    Button dateBtn;
    TextView selectedDateView;
    private EditText taskTitleEditText, noteEditText;
    private TextView dateInputTextView;
    String usernameValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        // folder spinner to select folder to save
        Spinner savedFolderSpinner = findViewById(R.id.savedFolder);
        // set up folder spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savedFolderSpinner.setAdapter(adapter);

        // open the database
        dbManager = new DBManager(this);
        db= new DB(this);
        dbManager.open();

        // get username value
        Intent intent = getIntent();
        String usernameValue = intent.getStringExtra("usernameValue");

        // get user ID based on username
        int userID = dbManager.getUserID(usernameValue);
        // get folders of each user to display in the folder spinner for user to select
        List<String> folderNames = db.getFolderNames(userID);

        // add this default folder if it doesnt exist
        folderNames.add("Not required folder");
        if (isFolderAlreadyExist("Not required folder", userID)){
            folderNames.remove("Not required folder");  // remove from the list if already exists to prevent duplication
        }else{
            // default folder for all users
            folderNames.add("Not required folder");   // if this task is not required to save in any folder

        }
        // if user wants to create a new folder
        folderNames.add("Create New Folder");
        // add the folders to the adapter
        adapter.addAll(folderNames);
        adapter.notifyDataSetChanged();

        // get the EditText for task title, date and notes
        taskTitleEditText = findViewById(R.id.titleInput);
        dateInputTextView = findViewById(R.id.selectedDate);
        noteEditText = findViewById(R.id.noteInput);

        // set up the OnItemSelectedListener for the spinner
        savedFolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedFolder = parentView.getItemAtPosition(position).toString();

                // insert into folder table while user choose this folder
                if ("Not required folder".equals(selectedFolder)) {
                    // check if "Not required folder" already exists for the user
                    if (!isFolderAlreadyExist("Not required folder", userID)) {
                        // if not, insert into the folder table
                        db.insertNewFolder("Not required folder", userID);
                    }

                }

                // if the "Create New Folder" option is selected
                // user will create a new folder
                if ("Create New Folder".equals(selectedFolder)) {
                    // dialog builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.create_new_folder, null);

                    final EditText editTextFolderName = dialogView.findViewById(R.id.newFolderName);

                    final AlertDialog dialog = builder.setView(dialogView).create();
                    Button create = dialogView.findViewById(R.id.createNewFolderBtn);

                    // user enter the new folder name
                    // when press create
                    create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String newFolderName = editTextFolderName.getText().toString().trim();
                            // if the input is not empty
                            if (!newFolderName.isEmpty()) {
                                // insert the new folder name into the database
                                db.insertNewFolder(newFolderName, userID);

                                // update the spinner with the new folder name
                                adapter.insert(newFolderName, adapter.getCount() - 1);
                                adapter.notifyDataSetChanged();

                                // set the newly added folder as selected
                                savedFolderSpinner.setSelection(adapter.getPosition(newFolderName));

                                // toast message when new folder is created
                                Toast.makeText(getApplicationContext(), "New Folder " + newFolderName + " inserted", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });

                    // if Cancel button is pressed
                    // close the dialog
                    Button cancel = dialogView.findViewById(R.id.cancelBtn);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // handle when nothing is selected in the spinner
            }
        });

        // when pressed the create task button
        Button createtaskbtn = findViewById(R.id.createTask);
        createtaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the id for all UI components
                EditText tasktitleinput = findViewById(R.id.titleInput);  // task title
                Button dateinput = findViewById(R.id.dateInput);  // due date
                String titlefieldinput = tasktitleinput.getText().toString().trim(); // get string for title
                TextView chosendate = findViewById(R.id.selectedDate);  // date display
                String dateText = chosendate.getText().toString().trim(); // get string for date display
                String dateview = "Date";
                RadioGroup radio = findViewById(R.id.radioGroup); // radio buttons for task difficulty/ priority selections
                // error message components
                TextView errorText = findViewById(R.id.errortext);

                // if title is empty
                if (titlefieldinput.isEmpty()) {

                    // error message
                    tasktitleinput.setError("Please enter the task title");
                    return;
                }


                // if date is empty
                if (dateText.contains(dateview)) {

                    // requestfocus() is used to enable the error message to be shown in textview
                    chosendate.requestFocus();
                    chosendate.setError("Please choose the date");
                    return;
                }

                else if (!dateText.contains(dateview)) {
                    chosendate.setError(null);
                }

                // check if any radio button in the RadioGroup is selected
                if (radio.getCheckedRadioButtonId() == -1) {
                    errorText.setError("Please choose the difficulty");
                    //errorIcon.setVisibility(View.VISIBLE);
                    errorText.requestFocus();
                    return;
                }

                else if(radio.getCheckedRadioButtonId() != -1){
                    errorText.setError(null);
                }

                // if all information are correct and valid, display successful message
                Snackbar.make(findViewById(android.R.id.content), "New Task Created Successfully!", Snackbar.LENGTH_SHORT).show();

                int listPos= 0;  // position in the list
                String taskTitle = taskTitleEditText.getText().toString();  // get the title string
                String selectedDate = dateInputTextView.getText().toString(); // get the date string
                listPos = TaskItem.savedtasklist.size() ;  // get the savedtasklist size
                String folder= savedFolderSpinner.getSelectedItem().toString(); // get the selected folder string in the spinner
                String note= noteEditText.getText().toString(); // get the note string
                RadioButton high= findViewById(R.id.highPrio); // get the high priority
                RadioButton low= findViewById(R.id.lowPrio); // get the low priority
                RadioButton medium= findViewById(R.id.mediumPrio); // get the medium priority
                String priority = "";
                String status= "";
                int priorityNo=0; // priority/ difficulty number
                int userId = dbManager.getUserID(usernameValue); // get the user ID

                // if high priority/ difficulty is selected
                if (high.isChecked()){

                    // pass priority text and priority number
                    priority="High Priority";
                    priorityNo= 1;
                    status="In Progress";  // default status for every task

                    // create an Intent to start the PriorityList activity
                    // display the newly created task in the list
                    Intent intent = new Intent(NewActivity.this, PriorityList.class);

                    // pass extra value to the intent
                    intent.putExtra("taskTitle", taskTitle);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedPriority", priority);
                    intent.putExtra("priorityNo", priorityNo);
                    intent.putExtra("listPos",listPos);
                    intent.putExtra("note",note);
                    intent.putExtra("selectedFolder", folder);
                    intent.putExtra("status", status);
                    intent.putExtra("usernameValue", usernameValue);
                    startActivity(intent);

                }

                // if medium priority/ difficulty is selected
                else if (medium.isChecked()){
                    // pass priority text and priority number
                    priority="Medium Priority";
                    priorityNo= 2;
                    status="In Progress"; // default status for every task

                    // create an Intent to start the PriorityList activity
                    Intent intent = new Intent(NewActivity.this, PriorityList.class);

                    // pass extra value to the intent
                    intent.putExtra("taskTitle", taskTitle);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedPriority", priority);
                    intent.putExtra("priorityNo", priorityNo);
                    intent.putExtra("listPos",listPos);
                    intent.putExtra("note",note);
                    intent.putExtra("selectedFolder", folder);
                    intent.putExtra("status", status);
                    intent.putExtra("usernameValue", usernameValue);
                    startActivity(intent);

                }

                // if low priority/ difficulty is selected
                else if (low.isChecked()) {
                    // pass priority text and priority number
                    priority = "Low Priority";
                    priorityNo= 3;
                    status="In Progress"; // default status for every task

                    // create an Intent to start the PriorityList activity
                    Intent intent = new Intent(NewActivity.this, PriorityList.class);

                    // pass extra value to the intent
                    intent.putExtra("taskTitle", taskTitle);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedPriority", priority);
                    intent.putExtra("priorityNo", priorityNo);
                    intent.putExtra("listPos",listPos);
                    intent.putExtra("note", note);
                    intent.putExtra("selectedFolder", folder);
                    intent.putExtra("status", status);
                    intent.putExtra("usernameValue", usernameValue);
                    startActivity(intent);

                }

                // declare LocalTime variable and get the current time
                LocalTime time = LocalTime.now();

                // convert to this pattern to store in Calendar table
                // to display the newly created task in calendar also (according to the chosen due date)
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a");
                String formattedTime = time.format(timeFormatter);
                String message = "You have created " + taskTitle + " in " + folder + ".";
                // insert the newly created task to the Task table
                dbManager.createtask(taskTitle, selectedDate, priority, priorityNo, listPos, folder, note, status, userId);
                // insert message while new task is created to Notification table
                dbManager.ActivityLog(message, userId, selectedDate);
                // insert the new task to the Calendar table
                dbManager.calendarEvent(taskTitle, selectedDate, formattedTime, userId);

            }

        });

        // date picker function (choose date)
        dateBtn = findViewById(R.id.dateInput);
        selectedDateView = findViewById(R.id.selectedDate);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // pop up date picker dialog to select the date, month and year
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewActivity.this,
                        R.style.CustomDatePickerDialogStyle,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // date chosen by the user
                                String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                // check if the selected date is not earlier than the current date
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, monthOfYear, dayOfMonth);
                                Calendar currentCalendar = Calendar.getInstance();
                                if (selectedCalendar.compareTo(currentCalendar) >= 0) {
                                    selectedDateView.setText(selectedDate);
                                } else {
                                    // handle the invalid selection
                                    Toast.makeText(NewActivity.this, "Please choose a date later than or equal to the current date", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        year, month, day);

                // set the minimum date to the current date, block the earlier date
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

                // display date picker dialog
                datePickerDialog.show();
            }
        });


        // return to the Priority List Page
        ImageButton backbutton = findViewById(R.id.backBtn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });

        // information icon (guidelines)
        ImageButton questionMarkButton = findViewById(R.id.questionMarkButton);
        questionMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuidelinesDialog();
            }
        });


    }

    // direct to Priority List
    public void goPrevious(){
        Intent intent = new Intent(this, PriorityList.class);
        intent.putExtra("usernameValue", usernameValue);
        startActivity(intent);
    }

    // check if "Not required folder" already exist
    private boolean isFolderAlreadyExist(String folderName, int userID) {
        return db.isFolderExist(folderName, userID);
    }

    // show folder guidelines
    private void showGuidelinesDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Saved Folder Guidelines");
        builder.setMessage("Hi buddies, you may start creating a new folder by pressing the Create New Folder option.\n\n" +
                "But once the new folder is created, it CANNOT be deleted.\n" +
                "Or you can choose the option of Not required folder if you do not want to save your task is any folders. \n" +
                "Later, you may review the list of task stored in each folder through the Folder Page.\n" +
                " \n" +
                " Enjoy your day! \n"
        );
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }



}














