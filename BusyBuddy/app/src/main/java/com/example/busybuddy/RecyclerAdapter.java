package com.example.busybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<TaskItem> itemList;  // default list to store task
    private List<TaskItem> filteredlist;  // list to store filtered task results (searching)
    private Context context;
    String progressitems[] = {"In Progress", "Completed"};  // two status for task
    ArrayList list= new ArrayList(Arrays.asList(progressitems));
    private String selectedStatus, selectedFolder;
    private DBManager dbManager;
    private DB db;
    private int userID;

    public RecyclerAdapter(List<TaskItem> itemList, Context context, DBManager dbManager, DB db, String selectedStatus, String selectedFolder, int userID) {
        this.itemList = itemList;
        this.context = context;
        this.filteredlist = new ArrayList<>(itemList);
        this.dbManager = dbManager;
        this.db= db;
        this.selectedStatus = selectedStatus;
        this.selectedFolder= selectedFolder;
        this.userID= userID;

    }

    // to filter the task
    public void setFilteredList(List<TaskItem> filteredlist) {
        this.filteredlist = filteredlist;
        notifyDataSetChanged();
    }


    // customized row design for recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate item layout and create a ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }


    // display the content
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind data to the ViewHolder
        TaskItem item = filteredlist.get(holder.getAdapterPosition());
        holder.title.setText(item.getTitle()); // set the title
        holder.due.setText(item.getDueDate()); // set the due date
        TaskItem task = itemList.get(holder.getAdapterPosition());

        //set the priority image based on the task's priority field
        String prioritySymbol = item.getPrioritySymbol();

        if ("High Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_red);
        } else if ("Medium Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_yellow);
        } else if ("Low Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_green);
        }

        // open the database
        dbManager = new DBManager(context);
        db= new DB(context);
        dbManager.open();

        // set up spinner for task status
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.progressSpinner.setAdapter(adapter);
        // get the index for selected status to maintain its position before any changes
        int statusIndex = getStatusIndexForSpinner(task.getStatus());
        holder.progressSpinner.setSelection(statusIndex);
        holder.progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TaskItem selectedTask = itemList.get(holder.getAdapterPosition()); // get the selected task
                String status = (String) parentView.getItemAtPosition(position);
                String title = selectedTask.getTitle();
                String date = selectedTask.getDueDate();
                int taskId = dbManager.getTaskIdByTitleAndDate(title, date); // retrieve the task id based on title and due date

                // if In Progress is chosen
                if (position == 0) {
                    // In progress
                    status = "In Progress";
                    // tick
                    holder.completedImage.setVisibility(View.INVISIBLE);

                // if Completed is chosen
                } else if (position == 1) {
                    // Completed
                    status = "Completed";
                    // tick icon for completed status
                    holder.completedImage.setVisibility(View.VISIBLE);

                }

                // if task id is found
                if (taskId != -1) {
                    selectedStatus = status;
                    db.updateTaskStatus(taskId, status); // update

                    // update the selected task's status
                    selectedTask.setStatus(status);

                    // set the spinner selection based on the updated status
                    holder.progressSpinner.setSelection(getStatusIndexForSpinner(status));
                } else {
                    // handle the case when the taskId is not found
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // handle case when nothing is selected
            }
        });

        // set up spinner for task folder
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.folderSpinner.setAdapter(adapter2);

        // get the list of folder for user from database
        List<String> folderNames = db.getFolderNames(userID);

        // add this folder for user to create a new folder from priority list
        folderNames.add("Create New Folder");

        adapter2.addAll(folderNames);
        adapter2.notifyDataSetChanged();

        // get the index for selected folder to maintain its position before any changes
        int folderIndex = getFolderIndexForSpinner(task.getFolder(), userID);
        holder.folderSpinner.setSelection(folderIndex);

        holder.folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            TaskItem selectedTask = itemList.get(holder.getAdapterPosition()); // get the selected task

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // handle Spinner item selection
                String folder = (String) parentView.getItemAtPosition(position);

                String title = selectedTask.getTitle();
                String date = selectedTask.getDueDate();
                int taskId = dbManager.getTaskIdByTitleAndDate(title, date);

                // if task id is found
                if (taskId != -1) {
                    selectedFolder = folder;
                    // update the new selected folder
                    db.updateTaskFolder(taskId, folder);
                    // update the selected task's folder
                    selectedTask.setFolder(folder);
                    // set the selected text to the savedfolder TextView
                    holder.savedfolder.setText(folder);

                    // if user wants to create a new folder
                    if (selectedFolder.equals("Create New Folder")) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View dialogView = inflater.inflate(R.layout.create_new_folder, null);

                        final EditText editTextFolderName = dialogView.findViewById(R.id.newFolderName);

                        final android.app.AlertDialog dialog = builder.setView(dialogView).create();
                        Button create = dialogView.findViewById(R.id.createNewFolderBtn);

                        // when Create button is pressed
                        create.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // get the input string
                                String newFolderName = editTextFolderName.getText().toString().trim();
                                if (!newFolderName.isEmpty()) {

                                    // insert the new folder name into the database
                                    db.insertNewFolder(newFolderName, userID);

                                    // update the spinner with the new folder name at the position before the Create New Folder
                                    // to make sure Create New Folder always be placed at the last position (convenience)
                                    adapter2.insert(newFolderName, adapter2.getCount() - 1);
                                    holder.folderSpinner.setSelection(adapter2.getCount() - 2); // select the newly added folder
                                    adapter2.notifyDataSetChanged();
                                    // display message while new folder is created
                                    Toast.makeText(context, "New Folder " + newFolderName + " inserted", Toast.LENGTH_SHORT).show();

                                }
                                dialog.dismiss();
                            }
                        });

                        // when Cancel button is pressed
                        Button cancel = dialogView.findViewById(R.id.cancelBtn);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // handle when nothing is selected
            }
        });


    }

    // get spinner index for status
    private int getStatusIndexForSpinner(String status) {

        List<String> spinnerStatus = Arrays.asList("In Progress", "Completed");

        // find the index of the status in the list
        int index = spinnerStatus.indexOf(status);

        if (index >= 0) {
            return index;
        } else {

            return 0;
        }
    }

    // get spinner index for folder
    private int getFolderIndexForSpinner(String folderName, int userID) {

        List<String> folderNames = db.getFolderNames(userID);
        // find the index of the folder name in the list
        int index = folderNames.indexOf(folderName);

        if (index >= 0) {
            return index;
        } else {
            return 0;
        }
    }


    // show recycler view item (task) details
    private void showDetails(TaskItem item) {
        // create and show a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.display_item, null);

        TextView titleTextView = dialogView.findViewById(R.id.tasknameview);
        TextView dueDateTextView = dialogView.findViewById(R.id.taskdueview);
        TextView difficulty = dialogView.findViewById(R.id.taskpriorityview);
        TextView note = dialogView.findViewById(R.id.noteview);
        TextView folder = dialogView.findViewById(R.id.taskfolderview);

        titleTextView.setText(item.getTitle());
        dueDateTextView.setText(item.getDueDate());
        note.setText(item.getNote());
        difficulty.setText(item.getPrioritySymbol());
        folder.setText(item.getFolder());

        builder.setView(dialogView);

        ImageButton cancelButton = dialogView.findViewById(R.id.cancel);

        AlertDialog alertDialog = builder.create(); // create the AlertDialog

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); // close the dialog
            }
        });

        alertDialog.show();
    }


    // get the list size
    @Override
    public int getItemCount() {
        return filteredlist.size();
    }


    public int getItemCount2() {
        return itemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerAdapter adapter;
        private List<String> foldernames;
        TextView title, due, savedfolder, savedfoldernew, note;
        Spinner progressSpinner, folderSpinner;
        ImageView priorityImage, completedImage;
        ImageButton showspinner, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.adapter = adapter;
            this.foldernames = foldernames;
            title = itemView.findViewById(R.id.taskTitle);
            due = itemView.findViewById(R.id.taskDue);
            note=itemView.findViewById(R.id.noteview);
            savedfolder= itemView.findViewById(R.id.folderview);
            showspinner=itemView.findViewById(R.id.showSpinnerButton);
            progressSpinner= itemView.findViewById(R.id.taskProgress);
            folderSpinner= itemView.findViewById(R.id.chosenFolder);
            priorityImage = itemView.findViewById(R.id.priorityImage);
            completedImage= itemView.findViewById(R.id.completedImage);

            // set up folder spinner
            showspinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    folderSpinner.performClick();
                }
            });


            itemView.setOnClickListener(this);
        }

        // show details for task item
        @Override
        public void onClick(View v) {
            // get the clicked item's position
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                TaskItem item = filteredlist.get(position);

                showDetails(item);
            }
        }
    }


}
