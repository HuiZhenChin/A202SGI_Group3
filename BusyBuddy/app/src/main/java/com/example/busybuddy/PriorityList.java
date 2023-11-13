package com.example.busybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.busybuddy.DB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Priority List (Display All Tasks Information)
public class PriorityList extends AppCompatActivity  {

    ListView listView;   // display search results
    List<TaskItem> itemList = new ArrayList<>();  // task list
    SearchView searchView;  // search view
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private DBManager dbManager;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.priority_list);

        // search components declaration
        listView = findViewById(R.id.searchList);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus(); // dismiss the keyboard (close the search view)

        // open the database
        dbManager = new DBManager(this);
        db= new DB(this);
        dbManager.open();

        // get username and password value
        Intent intentLogin = getIntent();
        String usernameValue = intentLogin.getStringExtra("usernameValue");
        String passwordValue = intentLogin.getStringExtra("passwordValue");

        // get the current task status and folder
        String selectedStatus = db.getSelectedStatus();
        String selectedFolder= db.getSelectedFolder();
        // get user ID
        int userID= dbManager.getUserID(usernameValue);

        // set up adapter for recyler view (to display task)
        recyclerAdapter = new RecyclerAdapter(itemList, this, dbManager, db, selectedStatus, selectedFolder, userID);
        recyclerAdapter = new RecyclerAdapter(TaskItem.savedtasklist, this, dbManager, db, selectedStatus, selectedFolder, userID);
        recyclerView = findViewById(R.id.recyclerView);

        // if user ID is found, display the task based on user ID
        if (userID != 0) {
            Cursor cursor = dbManager.displayTask(userID);

            // no task message textview and logo declaration
            TextView message = findViewById(R.id.noTaskMessage);
            ImageView bee = findViewById(R.id.imageBee);

            // if the database is not null, get the column index for each columns
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    int columnIndexTask = cursor.getColumnIndex(DB.TASK);
                    int columnIndexDueDate = cursor.getColumnIndex(DB.DUE_DATE);
                    int columnIndexDifficulty = cursor.getColumnIndex(DB.DIFFICULTY);
                    int columnIndexDifficultyNo= cursor.getColumnIndex(DB.DIFFICULTY_NO);
                    int columnIndexFolder = cursor.getColumnIndex(DB.FOLDER);
                    int columnIndexNote = cursor.getColumnIndex(DB.NOTE);
                    int columnIndexStatus = cursor.getColumnIndex(DB.STATUS);


                    if (cursor.getCount() == 0) {
                        // no data to display
                    } else {
                        // move the cursor to the first position
                        cursor.moveToFirst();

                    }

                    do {
                        // retrieve column indices only once
                        // avoid duplication of data
                        if (columnIndexTask != -1 && columnIndexDueDate != -1 &&
                                columnIndexDifficulty != -1 && columnIndexDifficultyNo != -1 && columnIndexFolder != -1 &&
                                columnIndexNote != -1 && columnIndexStatus!= -1) {

                            String task = cursor.getString(columnIndexTask);
                            String dueDate = cursor.getString(columnIndexDueDate);
                            String difficulty = cursor.getString(columnIndexDifficulty);
                            int priorityNumber = cursor.getInt(columnIndexDifficultyNo);
                            String taskfolder = cursor.getString(columnIndexFolder);
                            String tasknote = cursor.getString(columnIndexNote);
                            String taskstatus= cursor.getString(columnIndexStatus);

                            // check if the task is already in the list
                            boolean taskExists = false;
                            for (TaskItem taskItem : TaskItem.savedtasklist) {
                                if (taskItem.getTitle().equals(task)) {
                                    taskExists = true;
                                    break;
                                }
                            }

                            if (!taskExists) {
                                // create a TaskItem object and add it to the adapter
                                TaskItem newTaskItem = new TaskItem(task, null, dueDate, difficulty, priorityNumber, taskfolder, tasknote, taskstatus);
                                TaskItem.savedtasklist.add(newTaskItem);

                                // notify the adapter for data change and update UI
                                recyclerAdapter.notifyDataSetChanged();
                                // solve the bugs of when search only the task will exist
                                filterlist("");

                            }
                        } else {
                            // handle the case where one or more columns are not found
                        }
                    } while (cursor.moveToNext());
                } finally {
                    cursor.close(); // close the cursor
                }
            } else {
                // handle the case where there are no rows in the cursor
            }

            if (TaskItem.savedtasklist.isEmpty()) {
                // no data to display
                message.setVisibility(View.VISIBLE);
                bee.setVisibility(View.VISIBLE);
            } else {
                // hide no task message
                message.setVisibility(View.INVISIBLE);
                bee.setVisibility(View.INVISIBLE);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        //set up the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filter the ListView as the user types
                filterlist(newText);

                return true;
            }
        });

        //go back create task
        ImageButton add = findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass the username to NewActivity
                Intent intent = new Intent(PriorityList.this, NewActivity.class);
                intent.putExtra("usernameValue", usernameValue);
                intent.putExtra("passwordValue", passwordValue);
                startActivity(intent);
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

        // go back to MainActivity to access the navigation drawer
        ImageButton menubutton = findViewById(R.id.menuBtn);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain();
            }
        });

        // recycler view and item touch helper (drag and drop/ swipe to delete) set up
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    };

    // searching function
    private void filterlist(String text){
        List<TaskItem> filteredlist= new ArrayList<>();
        for (TaskItem taskItem: TaskItem.savedtasklist){
            // accept lower case
            if(taskItem.getTitle().toLowerCase().contains(text.toLowerCase())){

                filteredlist.add(taskItem);
            }
        }
        // filter the result
        if (recyclerAdapter != null) {
            recyclerAdapter.setFilteredList(filteredlist);
        }
    }

    // information guidelines for Priority List Page
    private void showGuidelinesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Priority List Guidelines");
        builder.setMessage("Hi buddies, you may start creating new task by pressing the + button.\n\n" +
                "Here are some special features available on this page.\n" +
                "1. You can change the task status from In Progress to Completed. \n" +
                "2. You can change the task folder from one to another, including create a new folder.\n" +
                "3. You can swipe left or right to remove the task from the list. \n" +
                "4. You can drag and drop (press on hold) to change the task priority arrangement.\n" +
                " \n" +
                "Red: High Priority | Yellow: Medium Priority | Green: Low Priority \n" +
                " \n" +
                " Enjoy your day! \n"
        );
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // return to Main Page
    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // colour initialization for different priority
    private int getPriorityImageResource(String priority) {
        if ("High Priority".equals(priority)) {
            return R.drawable.baseline_circle_red;
        } else if ("Medium Priority".equals(priority)) {
            return R.drawable.baseline_circle_yellow;
        } else if ("Low Priority".equals(priority)) {
            return R.drawable.baseline_circle_green;
        }
        return 0;
    }

    // Item Touch Helper
    // direction declaration
    // drag and drop direction
    int dragDirection = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
    // swipe to delete direction
    int swipeDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(dragDirection, swipeDirection) {

        // drag and drop
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            RecyclerView sourceRecyclerView = (RecyclerView) recyclerView;
            RecyclerView targetRecyclerView = (RecyclerView) target.itemView.getParent();

            int fromPosition = viewHolder.getAdapterPosition();   // where it is dragged from
            int toPosition = target.getAdapterPosition(); // where it will be dragged to
            String priority;
            int priorityNo;
            int taskId = 0;  // task id for dragged item
            int taskId2 = 0; // task id for moved item (the one which will swap position with dragged item)

            // check if the positions are valid
            if (fromPosition == RecyclerView.NO_POSITION || toPosition == RecyclerView.NO_POSITION) {
                return false;
            }

            // priority colour changes while dragging
            int totalTasks = recyclerView.getAdapter().getItemCount();
            int dropPosition = toPosition;

            // determine the priority based on the drop position
            if (dropPosition < totalTasks * 0.5) {  // less than 50% of the task (High Priority)
                priority = "High Priority";
                priorityNo = 1;
            } else if (dropPosition <= totalTasks * 0.7) {   // less than 70% of the task (Medium Priority)
                priority = "Medium Priority";
                priorityNo = 2;
            } else {
                priority = "Low Priority"; // less than that (Low Priority)
                priorityNo = 3;
            }

            // update the priority for the dropped task
            TaskItem droppedItem = TaskItem.savedtasklist.get(fromPosition); // use fromPosition for dropped item
            droppedItem.setPrioritySymbol(priority);  // update the latest colour changes
            String title = droppedItem.getTitle();   // get the task title
            String date = droppedItem.getDueDate();  // get the task due date
            taskId = db.getTaskIdByTitleAndDate(title, date);  // retrieve the task id

            TaskItem movedItem = TaskItem.savedtasklist.get(toPosition); // use toPosition for moved item
            movedItem.setPrioritySymbol(priority);
            String title2 = movedItem.getTitle();
            String date2 = movedItem.getDueDate();
            taskId2 = db.getTaskIdByTitleAndDate(title2, date2);

            // swap the position
            Collections.swap(TaskItem.savedtasklist, fromPosition, toPosition);

            // update the priority image for both source and target views
            // source view (when dragging)
            RecyclerAdapter.ViewHolder sourceViewHolder = (RecyclerAdapter.ViewHolder) viewHolder;
            sourceViewHolder.priorityImage.setImageResource(getPriorityImageResource(priority));

            // target view (when dropping)
            RecyclerAdapter.ViewHolder targetViewHolder = (RecyclerAdapter.ViewHolder) target;
            targetViewHolder.priorityImage.setImageResource(getPriorityImageResource(priority));

            // don't update the database with source positions, update for target positions
            dbManager.updateTaskPriority(taskId, priority, priorityNo);
            dbManager.updateTaskPriority(taskId2, priority, priorityNo);

            // update the order in the database using the final positions
            db.updateOrder(taskId, toPosition); // use toPosition for taskId
            db.updateOrder(taskId2, fromPosition); // use fromPosition for taskId2

            // notify for the item move after swapping the items
            recyclerAdapter.notifyItemMoved(fromPosition, toPosition);

            return true;


        }

        // swipe to delete
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (recyclerView != null) {
                int position = viewHolder.getAdapterPosition();  // get the task position

                String title = TaskItem.savedtasklist.get(position).getTitle();
                String date = TaskItem.savedtasklist.get(position).getDueDate();

                // get the selected task id
                int taskIdToDelete = dbManager.getTaskIdByTitleAndDate(title, date);

                if (taskIdToDelete != -1) {
                    // delete the task in database
                    db.deletetask(taskIdToDelete);
                    // remove from the list
                    TaskItem.savedtasklist.remove(position);
                    // solve the bugs of when delete the below item will replace the deleted item position (prevent empty row)
                    filterlist("");
                    // display message
                    Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Task Deleted", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        }


    };



}





