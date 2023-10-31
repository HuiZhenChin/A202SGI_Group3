package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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



public class PriorityList extends AppCompatActivity  {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<TaskItem> itemList = new ArrayList<>(); // Initialize the list with your data

    List<String> taskList;
    SearchView searchView;

    RecyclerView recyclerView, recyclerViewMenu;
    RecyclerAdapter recyclerAdapter;
    MenuAdapter menuAdapter;

    private DrawerLayout drawerLayout;

    //list to store newly created task
   // private List<TaskItem> savedtasklist = new ArrayList<>();

    String progressitems[] = {"In Progress", "Completed"};

    ArrayList list= new ArrayList(Arrays.asList(progressitems));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.priority_list);

        listView = findViewById(R.id.searchList);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

       // taskList = new ArrayList<>(Arrays.asList("Data Science", "Theory of Computation", "Cybersecurity", "Quiz", "Test", "Android Development Project", "Big Data Programming Project", "Advanced Algorithm Viva", "Software Engineering Project"));
        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        recyclerAdapter = new RecyclerAdapter(itemList, this);
        //listView.setAdapter(recyclerAdapter);
        recyclerAdapter = new RecyclerAdapter(TaskItem.savedtasklist, this);
        recyclerView = findViewById(R.id.recyclerView);
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
                // Filter the ListView as the user types
                filterlist(newText);

                return true;
            }
        });

        //go back create task

        ImageButton add = findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

/*
        drawerLayout = findViewById(R.id.drawer_layout);
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        View sideMenuLayout = findViewById(R.id.sideMenuLayout);

        // Set an OnClickListener for the menu button
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the navigation drawer
                sideMenuLayout.setVisibility(View.VISIBLE);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

*/

        //detect what user selects in the radio group
        //String highselectedPriority = getIntent().getStringExtra("selectedHighPriority");
       // String lowselectedPriority = getIntent().getStringExtra("selectedLowPriority");
        //String mediumselectedPriority = getIntent().getStringExtra("selectedMediumPriority");
        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra("taskTitle");
        String selectedDate = intent.getStringExtra("selectedDate");
        String priority=intent.getStringExtra("selectedPriority");
        String selectedFolder = intent.getStringExtra("selectedFolder");
        String note= intent.getStringExtra("note");
        TextView message= findViewById(R.id.noTaskMessage);
        ImageView bee= findViewById(R.id.imageBee);
        //intent.putExtra("title", taskTitle);
        //save data to SharedPreferences and pass to MenuActivity
       // SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
       // SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("title", taskTitle);
       // editor.putString("folder", selectedFolder);
        //editor.apply();


        if(taskTitle==null && selectedDate==null && priority ==null ){
            message.setVisibility(View.VISIBLE);
            bee.setVisibility(View.VISIBLE);
        }

        else if(taskTitle!=null && selectedDate!=null && priority !=null ) {
            TaskItem newTaskItem = new TaskItem(taskTitle, selectedDate, priority, selectedFolder, note);
            TaskItem.savedtasklist.add(newTaskItem);
        }
        Collections.sort(TaskItem.savedtasklist);
        recyclerAdapter.notifyDataSetChanged();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);





        //TextView message= findViewById(R.id.noTaskMessage);

/*
        //when user select low difficulty
        if(lowselectedPriority!=null){
            savedtasklist.add(new TaskItem(taskTitle,selectedDate,"Low Priority"));

        }

        //when user select medium difficulty
        else if (mediumselectedPriority!=null){
            savedtasklist.add(new TaskItem(taskTitle,selectedDate,"Medium Priority"));

        }

        //when user select high difficulty
        else if (highselectedPriority !=null){
            savedtasklist.add(new TaskItem(taskTitle,selectedDate,"High Priority"));
        }


       else if (savedtasklist.isEmpty()){

           message.setVisibility(View.VISIBLE);
           bee.setVisibility(View.VISIBLE);
       }

*/
        //detect the selected priority and show the corresponding TextView
        //testing purpose
     /*   else if (lowselectedPriority == null && mediumselectedPriority == null && highselectedPriority == null) {

            savedtasklist.add(new TaskItem("Theory of Computation Test", "21-12-2023", "High Priority"));
            savedtasklist.add(new TaskItem("B Test", "23-12-2023", "High Priority"));
            savedtasklist.add(new TaskItem("C Test", "25-12-2023", "High Priority"));
            savedtasklist.add(new TaskItem("D Test", "27-12-2023", "High Priority"));
            savedtasklist.add(new TaskItem("E Test", "29-12-2023", "Medium Priority"));
            savedtasklist.add(new TaskItem("F Test", "30-12-2023", "Medium Priority"));
            savedtasklist.add(new TaskItem("G Test", "21-12-2023", "Medium Priority"));
            savedtasklist.add(new TaskItem("H Test", "21-12-2023", "Low Priority"));
            savedtasklist.add(new TaskItem("I Test", "21-12-2023", "Low Priority"));
            savedtasklist.add(new TaskItem("J Test", "21-12-2023", "Low Priority"));
            savedtasklist.add(new TaskItem("K Test", "21-12-2023", "Low Priority"));
            savedtasklist.add(new TaskItem("Cybersecurity Test", "21-12-2023", "Low Priority"));

        }

*/

    //recyclerView.setAdapter(recyclerAdapter);
    //recyclerAdapter.notifyDataSetChanged();


    };

    private void filterlist(String text){
        List<TaskItem> filteredlist= new ArrayList<>();
        for (TaskItem taskItem: TaskItem.savedtasklist){
            if(taskItem.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(taskItem);
            }
        }

        if (recyclerAdapter != null) {
            recyclerAdapter.setFilteredList(filteredlist);
        }
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

    public void openNewActivity2(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    int dragDirection = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
    int swipeDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(dragDirection, swipeDirection) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // Determine the source and target RecyclerViews
            RecyclerView sourceRecyclerView = (RecyclerView) recyclerView;
            RecyclerView targetRecyclerView = (RecyclerView) target.itemView.getParent();

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            int totalTasks = recyclerView.getAdapter().getItemCount();
            int dropPosition = (fromPosition + toPosition) / 2;

            // Determine the priority based on the drop position
            String priority;
            if (dropPosition < totalTasks * 0.5) {
                priority = "High Priority";
            } else if (dropPosition <= totalTasks * 0.7) {
                priority = "Medium Priority";
            } else {
                priority = "Low Priority";
            }

            //update the priority for the dropped task
            TaskItem droppedItem = TaskItem.savedtasklist.get(fromPosition);
            droppedItem.setPrioritySymbol(priority);

            //notify for the item move
            Collections.swap(TaskItem.savedtasklist, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            //update the priority image for both source and target views
            // Source view (when dragging)
            RecyclerAdapter.ViewHolder sourceViewHolder = (RecyclerAdapter.ViewHolder) viewHolder;
            sourceViewHolder.priorityImage.setImageResource(getPriorityImageResource(priority));

            // Target view (when dropping)
            RecyclerAdapter.ViewHolder targetViewHolder = (RecyclerAdapter.ViewHolder) target;
            targetViewHolder.priorityImage.setImageResource(getPriorityImageResource(priority));

            return true;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //handle deletion

            if (recyclerView != null) {
                int position = viewHolder.getAdapterPosition();

                TaskItem.savedtasklist.remove(position);

                recyclerView.getAdapter().notifyItemRemoved(position);

                Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Task Deleted", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }


        }

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

    };



}




