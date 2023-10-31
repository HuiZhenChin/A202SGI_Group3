package com.example.busybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuItemModel> itemList;
    private Context context;



    public MenuAdapter(List<MenuItemModel> itemList, Context context) {

        this.itemList = itemList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate item layout and create a ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind data to the ViewHolder
        MenuItemModel item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.folder.setText(item.getFolder());

        //set the priority image based on the task's priority field
       /* String prioritySymbol = item.getPrioritySymbol();

        if ("High Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_red);
        } else if ("Medium Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_yellow);
        } else if ("Low Priority".equals(prioritySymbol)) {
            holder.priorityImage.setImageResource(R.drawable.baseline_circle_green);
        }
*/


    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, folder;

        ImageView priorityImage, completedImage;

        Spinner folderSpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            folder = itemView.findViewById(R.id.savedFolder);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Handle item click
        }
    }
}
