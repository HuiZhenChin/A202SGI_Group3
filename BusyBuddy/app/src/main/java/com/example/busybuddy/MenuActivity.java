package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMenu;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu);

        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra("taskTitle");
        String taskFolder = intent.getStringExtra("selectedFolder");
        //String selectedDate = intent.getStringExtra("selectedDate");
        //String priority=intent.getStringExtra("selectedPriority");


        MenuItemModel menuItemModel = new MenuItemModel(taskTitle, taskFolder);
        if (taskTitle == null && taskFolder == null) {

        } else if (taskTitle != null && taskFolder == null) {

            MenuItemModel.menulist.add(menuItemModel);
        }

        menuAdapter.notifyDataSetChanged();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewMenu.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewMenu);

    }

    int dragDirection = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
    int swipeDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(dragDirection, swipeDirection) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerViewMenu, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // Determine the source and target RecyclerViews
            RecyclerView sourceRecyclerView = (RecyclerView) recyclerViewMenu;
            RecyclerView targetRecyclerView = (RecyclerView) target.itemView.getParent();

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            int totalTasks = recyclerViewMenu.getAdapter().getItemCount();
            int dropPosition = (fromPosition + toPosition) / 2;


            //update the priority for the dropped task
            MenuItemModel droppedItem = MenuItemModel.menulist.get(fromPosition);
            //droppedItem.setPrioritySymbol(priority);

            //notify for the item move
            Collections.swap(MenuItemModel.menulist, fromPosition, toPosition);
            recyclerViewMenu.getAdapter().notifyItemMoved(fromPosition, toPosition);

            //update the priority image for both source and target views
            // Source view (when dragging)
            RecyclerAdapter.ViewHolder sourceViewHolder = (RecyclerAdapter.ViewHolder) viewHolder;
           // sourceViewHolder.priorityImage.setImageResource(getPriorityImageResource(priority));

            // Target view (when dropping)
            RecyclerAdapter.ViewHolder targetViewHolder = (RecyclerAdapter.ViewHolder) target;
            //targetViewHolder.priorityImage.setImageResource(getPriorityImageResource(priority));

            return true;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //handle deletion

            if (recyclerViewMenu != null) {
                int position = viewHolder.getAdapterPosition();

                MenuItemModel.menulist.remove(position);

                recyclerViewMenu.getAdapter().notifyItemRemoved(position);

                Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Task Deleted", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

        }

    };

}





