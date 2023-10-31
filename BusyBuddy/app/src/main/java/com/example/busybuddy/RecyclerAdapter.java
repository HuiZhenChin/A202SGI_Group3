package com.example.busybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<TaskItem> itemList;
    private List<TaskItem> filteredlist;
    private Context context;
    String progressitems[] = {"In Progress", "Completed"};
    ArrayList list= new ArrayList(Arrays.asList(progressitems));

    String items[] = {"Assignment", "Work", "Personal", "Create New Folder"};

    ArrayList list2= new ArrayList(Arrays.asList(items));
    public RecyclerAdapter(List<TaskItem> itemList, Context context) {

        this.itemList = itemList;
        this.context= context;
        this.filteredlist = new ArrayList<>(itemList);
    }

    public void setFilteredList(List<TaskItem> filteredlist) {
        this.filteredlist = filteredlist;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate item layout and create a ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind data to the ViewHolder
        TaskItem item = filteredlist.get(position);
        holder.title.setText(item.getTitle());
        holder.due.setText(item.getDueDate());
        holder.savedfolder.setText(item.getFolder());

        //holder.savedfolder.setVisibility(View.VISIBLE);
        //holder.savedfoldernew.setVisibility(View.INVISIBLE);

        //set the priority image based on the task's priority field
        String prioritySymbol = item.getPrioritySymbol();

        if ("High Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_red);
        } else if ("Medium Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_yellow);
        } else if ("Low Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_green);
        }

        //progress dropdown list
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.progressSpinner.setAdapter(adapter);

        holder.progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //handle Spinner item selection
                if (position == 0) {
                    // In progress
                } else if (position == list.size() - 1) {
                    // Completed

                    holder.completedImage.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //handle when nothing is selected
            }
        });

        ArrayAdapter adapter2 = new ArrayAdapter(context, android.R.layout.simple_spinner_item, list2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.folderSpinner.setAdapter(adapter2);


        holder.folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //handle Spinner item selection
                String selectedText = (String) parentView.getItemAtPosition(position);

                // Set the selected text to the savedfolder TextView
                holder.savedfoldernew.setText(selectedText);
               // holder.savedfolder.setVisibility(View.INVISIBLE);
               // holder.savedfoldernew.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //handle when nothing is selected
            }
        });



    }



    //show recycler view details
    private void showDetails(TaskItem item) {
        //create and show a dialog
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

        AlertDialog alertDialog = builder.create(); // Create the AlertDialog

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); // Close the dialog
            }
        });

        alertDialog.show();
    }



    @Override
    public int getItemCount() {
        return filteredlist.size();
    }

   /* @Override
    public int getItemCount2() {
        return itemList.size();
    }

    */


    public void filter(String text) {
        //clear the filtered list
        filteredlist.clear();

        //filter the items based on the search text
        for (TaskItem taskItem : itemList) {
            if (taskItem.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(taskItem);
            }
        }

        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerAdapter adapter;
        TextView title, due, savedfolder, savedfoldernew, note;
        Spinner progressSpinner, folderSpinner;

        ImageView priorityImage, completedImage;

        ImageButton showspinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.adapter = adapter;
            title = itemView.findViewById(R.id.taskTitle);
            due = itemView.findViewById(R.id.taskDue);
            note=itemView.findViewById(R.id.noteview);
            savedfolder= itemView.findViewById(R.id.folderview);
            savedfoldernew=itemView.findViewById(R.id.folderviewnew);
            showspinner=itemView.findViewById(R.id.showSpinnerButton);
            progressSpinner= itemView.findViewById(R.id.taskProgress);
            folderSpinner= itemView.findViewById(R.id.chosenFolder);
            priorityImage = itemView.findViewById(R.id.priorityImage);
            completedImage= itemView.findViewById(R.id.completedImage);

            showspinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    folderSpinner.performClick();
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //get the clicked item's position
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                TaskItem item = filteredlist.get(position);

                showDetails(item);
            }
        }
    }


}
