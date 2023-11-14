package com.example.busybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// custom adapter for DisplayFolder class
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<TaskItem> taskItems;
    private Context context;

    public FolderAdapter(Context context, List<TaskItem> taskItems) {
        this.context = context;
        this.taskItems = taskItems;  // list of tasks
    }

    // call the designed row UI (folder_item) in recycler view
    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        TaskItem currentTask = taskItems.get(position);
        TaskItem previousTask = position > 0 ? taskItems.get(position - 1) : null;

        // example format:
        /* Folder Name 1         Task 1
                                 Task 2
                                 Task 3
           Folder Name 2         Task 4
           Folder Name 3         Task 5
                                 Task 6
        */
        // display folder only if it's the first item in the group or if the folder changes
        if (previousTask == null || !currentTask.getFolder().equals(previousTask.getFolder())) {
            holder.folderNameTextView.setVisibility(View.VISIBLE);
            holder.folderNameTextView.setText(currentTask.getFolder());
        } else {
            // hide the folder name for subsequent items with the same folder
            holder.folderNameTextView.setVisibility(View.GONE);
        }

        // always display task name
        holder.taskNameTextView.setText(currentTask.getTitle());
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {

        TextView folderNameTextView;
        TextView taskNameTextView;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderNameTextView = itemView.findViewById(R.id.folderNameTextView);
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
        }
    }
}
